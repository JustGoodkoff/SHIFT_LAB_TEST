package com.example.cft_test

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(this)).get(MainViewModel::class.java)
        if (viewModel.checkIfUserExists()) {
            val intent = Intent(this@MainActivity, GreetingActivity::class.java)
            startActivity(intent)
            finish()
        }

        var isNameValid = false
        var isSurnameValid = false
        var isDateValid = false
        var isPasswordValid = false
        var isConfirmPasswordValid = false
        binding.registrationButton.isEnabled = false

        binding.registrationButton.setOnClickListener {
            viewModel.createUser(
                binding.name.text.toString(),
                binding.surname.text.toString(),
                binding.date.text.toString(),
                binding.password.text.toString()
            )
            val intent = Intent(this@MainActivity, GreetingActivity::class.java)
            startActivity(intent)
            finish()
        }

        val constraints =
            CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_date_of_birth))
                .setCalendarConstraints(constraints.build())
                .build()

        datePicker.addOnPositiveButtonClickListener {
            val instant = Instant.ofEpochMilli(datePicker.selection!!)
            binding.date.setText(
                SimpleDateFormat(
                    getString(R.string.date_format),
                    Locale.getDefault()
                ).format(Date.from(instant))
            )
        }

        binding.dateLayout.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, "")
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
                        isNameValid =
                            viewModel.validateName(binding.name.text.toString(), binding.nameLayout)
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
                        isSurnameValid = viewModel.validateName(
                            binding.surname.text.toString(),
                            binding.surnameLayout
                        )
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
                        isDateValid =
                            viewModel.validateDate(binding.date.text.toString(), binding.dateLayout)
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
                        isPasswordValid = viewModel.validatePassword(
                            binding.password.text.toString(),
                            binding.passwordLayout
                        )
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
                            viewModel.validateConfirmPassword(
                                binding.confirmPassword.text.toString(),
                                binding.password.text.toString(),
                                binding.confirmPasswordLayout
                            )
                        validateAllData(
                            binding.registrationButton, isNameValid, isSurnameValid,
                            isPasswordValid, isConfirmPasswordValid, isDateValid
                        )
                    }
                }.start()
            }
        })
    }


    private fun validateAllData(regButton: MaterialButton, vararg isValid: Boolean) {
        regButton.isEnabled = isValid.all { it }
    }
}
