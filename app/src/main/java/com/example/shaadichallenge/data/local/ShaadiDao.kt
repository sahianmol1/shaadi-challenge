package com.example.shaadichallenge.data.local

import androidx.room.*

@Dao
interface ShaadiDao {

    @Query("SELECT * FROM shaadi_profile")
    suspend fun getAllProfiles(): List<ShaadiProfileEntity>?

    @Insert
    suspend fun insertProfile(profile: ShaadiProfileEntity)

    @Update
    suspend fun updateProfile(profile: ShaadiProfileEntity)

    @Query("DELETE FROM shaadi_profile")
    suspend fun deleteProfiles()
}