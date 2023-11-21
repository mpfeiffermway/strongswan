package org.strongswan.android.logic;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.strongswan.android.data.UserCertificate;
import org.strongswan.android.utils.KeyPair;
import org.strongswan.android.utils.KeyPairs;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import androidx.annotation.NonNull;

public class UserCertificateInstaller
{
	private static final String TAG = UserCertificateInstaller.class.getSimpleName();

	private final DevicePolicyManager policyManager;

	public UserCertificateInstaller(final Context context)
	{
		this.policyManager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	}

	private boolean installKeyPair(@NonNull UserCertificate userCertificate, @NonNull KeyPair keyPair)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
		{
			int flags = DevicePolicyManager.INSTALLKEY_REQUEST_CREDENTIALS_ACCESS | DevicePolicyManager.INSTALLKEY_SET_USER_SELECTABLE;
			return policyManager.installKeyPair(
				null,
				keyPair.privateKey,
				new Certificate[]{keyPair.certificate},
				userCertificate.getAlias(),
				flags);
		}
		else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			return policyManager.installKeyPair(
				null,
				keyPair.privateKey,
				new Certificate[]{keyPair.certificate},
				userCertificate.getAlias(),
				true);
		}

		// This effectively prevents the app from using its own certificate, so certificate based authentication can only really work on Android 6+
		// The certificate chooser is currently never shown on devices that are enrolled
		return policyManager.installKeyPair(
			null,
			keyPair.privateKey,
			keyPair.certificate,
			userCertificate.getAlias());
	}

	private boolean installKeyPair(@NonNull UserCertificate userCertificate)
		throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException
	{
		final KeyPair keyPair = KeyPairs.from(userCertificate);
		if (keyPair == null)
		{
			return false;
		}

		return installKeyPair(userCertificate, keyPair);
	}

	private void removeKeyPair(@NonNull UserCertificate userCertificate)
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
		{
			Log.w(TAG, "Cannot remove key pair, unsupported on API level " + Build.VERSION.SDK_INT);
			return;
		}

		policyManager.removeKeyPair(null, userCertificate.getAlias());
	}

	public synchronized boolean tryInstall(@NonNull UserCertificate userCertificate)
	{
		try
		{
			return installKeyPair(userCertificate);
		}
		catch (final Exception e)
		{
			Log.e(TAG, "Could not install key pair " + userCertificate.getAlias(), e);
			return false;
		}
	}

	public synchronized void tryRemove(@NonNull UserCertificate userCertificate)
	{
		try
		{
			removeKeyPair(userCertificate);
		}
		catch (final Exception e)
		{
			Log.e(TAG, "Could not remove key pair " + userCertificate.getAlias(), e);
		}
	}
}
