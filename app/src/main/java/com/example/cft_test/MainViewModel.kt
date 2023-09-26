package com.example.cft_test

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.cft_test.db.User
import com.example.cft_test.db.UserAuthDB
import com.google.android.material.textfield.TextInputLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(context: Context) : ViewModel() {

    private val db = Room.databaseBuilder(
        context,
        UserAuthDB::class.java, "userData"
    ).allowMainThreadQueries().build()

    fun createUser(name: String, surname: String, date: String, password: String) {
        val userDao = db.userDao()
        userDao.insert(User(0, name, surname, date, password))
    }

    fun checkIfUserExists(): Boolean {
        val userDao = db.userDao()
        return userDao.getUser().isNotEmpty()
    }

    fun validateName(name: String, nameLayout: TextInputLayout): Boolean {
        if (name.isNotEmpty()) {
            if (name.length < 2) {
                nameLayout.error = "Минимум 2 символа"
            } else {
                nameLayout.error = null
                return true
            }
        } else {
            nameLayout.error = null
        }
        return false
    }

    fun validatePassword(password: String, passwordLayout: TextInputLayout): Boolean {
        if (password.isNotEmpty()) {
            if (password.length < 6) {
                passwordLayout.error = "Пароль должен содержать не менее 6 символов"
            } else if (!password.any { it.isDigit() }) {
                passwordLayout.error = "Пароль должен содержать цифры"
            } else if (!(password.any { it.isUpperCase() }
                        && password.any { it.isLowerCase() })
            ) {
                passwordLayout.error =
                    "Пароль должен содержать буквы верхнего и нижнего регистра"
            } else {
                passwordLayout.error = null
                return true
            }
        } else {
            passwordLayout.error = null
        }
        return false
    }

    fun validateDate(date: String, dateLayout: TextInputLayout): Boolean {
        if (!date.isNullOrEmpty()) {
            val parsedDate: Date?
            try {
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                format.isLenient = false
                parsedDate = format.parse(date)
                if (parsedDate.after(Date())) {
                    dateLayout.error = "Указана дата после сегодняшней"
                } else {
                    dateLayout.error = null
                    return true
                }
            } catch (_: ParseException) {
                dateLayout.error = "Некорректный формат даты"
            }
        } else {
            dateLayout.error = null
        }
        return false
    }

    fun validateConfirmPassword(
        confirmPassword: String,
        password: String,
        confirmPasswordLayout: TextInputLayout
    ): Boolean {
        if (confirmPassword.isNotEmpty()) {
            if (confirmPassword == password) {
                confirmPasswordLayout.error = null
                return true
            } else {
                confirmPasswordLayout.error = "Пароли не совпадают!"
            }
        } else {
            confirmPasswordLayout.error = null
        }
        return false
    }

}