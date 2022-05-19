package com.example.shaadichallenge.repository

import com.example.shaadichallenge.data.local.ShaadiDao
import com.example.shaadichallenge.data.local.ShaadiProfileEntity
import com.example.shaadichallenge.data.remote.api.ShaadiApi
import com.example.shaadichallenge.utils.intoDateFormat
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShaadiRepositoryImpl @Inject constructor(
    private val api: ShaadiApi,
    private val dao: ShaadiDao
) : ShaadiRepository {

    override suspend fun getProfiles(isRefresh: Boolean): List<ShaadiProfileEntity> = withContext(
        Dispatchers.IO
    ) {

        if (!dao.getAllProfiles().isNullOrEmpty() && !isRefresh) {
            return@withContext dao.getAllProfiles()!!
        }

        val response = api.getProfiles()
        val deferreds = mutableListOf<Deferred<ShaadiProfileEntity>>()
        if (response.isSuccessful && response.body() != null) {

            if (isRefresh) {
                dao.deleteProfiles()
            }

            for (profile in response.body()!!.results) {
                deferreds.add(
                    async {
                        with(profile) {
                            return@async ShaadiProfileEntity(
                                id = login.username,
                                name = name.first + " " + name.last,
                                picture = picture.large,
                                dob = dob.date.intoDateFormat(),
                                age = "Age: " + dob.age,
                                city = location.city,
                                country = location.country,
                                phone = "Phone: $phone",
                                gender = gender,
                                isAccepted = null
                            )
                        }
                    }
                )
            }
        }

        for (deferred in deferreds) {
            dao.insertProfile(deferred.await())
        }

        return@withContext dao.getAllProfiles() ?: emptyList()

    }

    override suspend fun updateProfile(profile: ShaadiProfileEntity): List<ShaadiProfileEntity> {
        dao.updateProfile(profile)
        return dao.getAllProfiles() ?: emptyList()
    }
}