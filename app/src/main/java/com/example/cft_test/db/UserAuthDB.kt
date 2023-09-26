package com.example.cft_test.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserAuthDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}