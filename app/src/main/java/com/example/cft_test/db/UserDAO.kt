package com.example.cft_test.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): List<User>

    @Insert
    fun insert(user: User)

    @Query("DELETE FROM user")
    fun delete()
}