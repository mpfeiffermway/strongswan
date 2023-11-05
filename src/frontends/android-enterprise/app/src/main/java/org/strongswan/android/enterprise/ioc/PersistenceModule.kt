package org.strongswan.android.enterprise.ioc

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.strongswan.android.enterprise.io.room.StrongSwanDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

	@Provides
	@Singleton
	fun strongSwanDatabase(@ApplicationContext context: Context): StrongSwanDatabase = Room
		.databaseBuilder(context, StrongSwanDatabase::class.java, "strongswan.db")
		.build()

	@Provides
	fun vpnProfileDao(strongSwanDatabase: StrongSwanDatabase) = strongSwanDatabase
		.vpnProfileDao()
}
