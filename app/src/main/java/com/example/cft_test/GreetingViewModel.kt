package com.example.cft_test

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.cft_test.db.UserAuthDB

class GreetingViewModel(context: Context): ViewModel() {

    private val db = Room.databaseBuilder(context,
        UserAuthDB::class.java, "userData"
    ).allowMainThreadQueries().build()

    fun getGreetingMessage(): String {
        return "Приветствую, " + db.userDao().getUser()[0].name.toString()
    }

    fun exit() {
        db.userDao().delete()
    }

}