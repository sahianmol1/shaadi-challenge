package com.example.shaadichallenge.data.remote.models

import com.anmol.shaadichallenge.models.Dob
import com.example.shaadichallenge.data.local.Gender
import com.example.shaadichallenge.data.remote.models.*

data class Result(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: Gender,
    val id: Id,
    val login: Login,
    val location: Location,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
)