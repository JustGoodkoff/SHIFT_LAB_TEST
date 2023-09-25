package com.example.cft_test

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cft_test.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // кнопка регистрации будет разблокирована только тогда, когда все данные буду валидны
        var isNameValid = false
        var isSurnameValid = false
        var isDateValid = false
        var isPasswordValid = false
        var isConfirmPasswordValid = false
        binding.registrationButton.isEnabled = false

        val constraints =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraints.build())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val instant = Instant.ofEpochMilli(datePicker.selection!!)
            binding.date.setText(
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date.from(instant))
            )
        }

        binding.dateLayout.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, "123")
        }

        binding.name.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        isNameValid = binding.name.validateName(binding.nameLayout)
                        validateAllData(
                            binding.registrationButton, isNameValid, isSurnameValid,
                            isPasswordValid, isConfirmPasswordValid, isDateValid
                        )
                    }
                }.start()
            }
        })


        binding.surname.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        isSurnameValid = binding.surname.validateName(binding.surnameLayout)
                        validateAllData(
                            binding.registrationButton, isNameValid, isSurnameValid,
                            isPasswordValid, isConfirmPasswordValid, isDateValid
                        )
                    }
                }.start()
            }
        })


        binding.date.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        isDateValid = binding.date.validateDate(binding.dateLayout)
                        validateAllData(
                            binding.registrationButton, isNameValid, isSurnameValid,
                            isPasswordValid, isConfirmPasswordValid, isDateValid
                        )
                    }
                }.start()
            }
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        isPasswordValid = binding.password.validatePassword(binding.passwordLayout)
                        validateAllData(
                            binding.registrationButton, isNameValid, isSurnameValid,
                            isPasswordValid, isConfirmPasswordValid, isDateValid
                        )
                    }
                }.start()
            }
        })

        binding.confirmPassword.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        isConfirmPasswordValid =
                            binding.confirmPassword.validateConfirmPassword(binding.confirmPasswordLayout, binding.password)
                        validateAllData(
                            binding.registrationButton, isNameValid, isSurnameValid,
                            isPasswordValid, isConfirmPasswordValid, isDateValid
                        )
                    }
                }.start()
            }
        })
    }


    // функции валидации введенных данных

    private fun validateAllData(regButton: MaterialButton, vararg isValid: Boolean) {
        for (i in isValid) {
            if (!i) {
                regButton.isEnabled = false
                return
            }
        }
        regButton.isEnabled = true
        Log.e("234", isValid.all { true }.toString())
    }

    private fun TextInputEditText.validatePassword(passwordLayout: TextInputLayout): Boolean {
        if (this.text.toString().isNotEmpty()) {
            if (this.text.toString().length < 6) {
                passwordLayout.error = context.getString(R.string.password_rule_six_symbols)
            } else if (!this.text.toString().any { it.isDigit() }) {
                passwordLayout.error = context.getString(R.string.password_rule_contains_number)
            } else if (!(this.text.toString().any { it.isUpperCase() }
                        && this.text.toString().any { it.isLowerCase() })
            ) {
                passwordLayout.error =
                    context.getString(R.string.password_rule_all_character_cases)
            } else {
                passwordLayout.error = null
                return true
            }
        } else {
            passwordLayout.error = null
        }
        return false
    }

    private fun TextInputEditText.validateName(nameLayout: TextInputLayout): Boolean {
        if (!this.text.isNullOrEmpty()) {
            if (this.text!!.length < 2) {
                nameLayout.error = context.getString(R.string.name_rule_2_symbols)
            } else {
                nameLayout.error = null
                return true
            }
        } else {
            nameLayout.error = null
        }
        return false
    }


    private fun TextInputEditText.validateDate(dateLayout: TextInputLayout): Boolean {
        if (!this.text.isNullOrEmpty()) {
            val parsedDate: Date?
            try {
                val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                format.isLenient = false
                parsedDate = format.parse(this.text.toString())
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

    private fun TextInputEditText.validateConfirmPassword(
        confirmPasswordLayout: TextInputLayout,
        password: TextInputEditText
    ): Boolean {
        if (this.text.toString().isNotEmpty()) {
            if (this.text.toString() == password.text.toString()) {
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
