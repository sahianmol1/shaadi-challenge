package com.example.shaadichallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShaadiProfileEntity::class], version = 1)
abstract class ShaadiDatabase : RoomDatabase() {
    abstract fun shaadiDao(): ShaadiDao
}