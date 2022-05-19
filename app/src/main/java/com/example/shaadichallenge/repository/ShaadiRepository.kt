package com.example.shaadichallenge.repository

import com.example.shaadichallenge.data.local.ShaadiProfileEntity

interface ShaadiRepository {

    suspend fun getProfiles(isRefresh: Boolean): List<ShaadiProfileEntity>

    suspend fun updateProfile(profile: ShaadiProfileEntity): List<ShaadiProfileEntity>
}