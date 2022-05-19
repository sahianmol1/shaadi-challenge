package com.example.shaadichallenge.data.remote.api

import com.example.shaadichallenge.data.remote.models.UserProfiles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShaadiApi {

    @GET("api/")
    suspend fun getProfiles(
        @Query("results") results: Int = 10
    ): Response<UserProfiles>
}