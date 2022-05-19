package com.example.shaadichallenge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shaadi_profile")
data class ShaadiProfileEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val picture: String,
    val dob: String,
    val age: String,
    val city: String,
    val country: String,
    val phone: String,
    val gender: Gender,
    var isAccepted: Boolean?
)
