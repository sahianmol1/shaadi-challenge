package com.example.shaadichallenge.data.remote.models

import com.example.shaadichallenge.data.remote.models.Info
import com.example.shaadichallenge.data.remote.models.Result

data class UserProfiles(
    val info: Info,
    val results: List<Result>
)