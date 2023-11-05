package org.strongswan.android.enterprise.io.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.strongswan.android.enterprise.io.entities.VpnProfile

@Dao
interface VpnProfileDao {

	@Query(
		"""
			SELECT *
				FROM VpnProfile
				ORDER BY name
		"""
	)
	fun fetchAll(): LiveData<List<VpnProfile>>

	@Query(
		"""
			SELECT *
				FROM VpnProfile
				WHERE _id = :id
		"""
	)
	fun fetchById(id: Long): LiveData<VpnProfile?>

	@Insert
	suspend fun insert(vpnProfile: VpnProfile): Long

	@Update
	suspend fun update(vpnProfile: VpnProfile)

	@Delete
	suspend fun delete(vpnProfile: VpnProfile)
}
