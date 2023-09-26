package com.example.cft_test

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.cft_test.db.User
import com.example.cft_test.db.UserAuthDB

class MainViewModel(context: Context): ViewModel() {

    private val db = Room.databaseBuilder(
        context,
        UserAuthDB::class.java, "userData"
    ).allowMainThreadQueries().build()

    fun createUser(name: String, surname: String, date: String, password: String) {
        val userDao = db.userDao()
        userDao.insert(User(0, name, surname, date, password))
    }

    fun checkIfUserExists(): Boolean{
        val userDao = db.userDao()
        return userDao.getUser().isNotEmpty()
    }

}