package com.example.cft_test

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.cft_test.databinding.ActivityGreetingBinding

class GreetingActivity : AppCompatActivity() {

    private lateinit var viewModel: GreetingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityGreetingBinding = ActivityGreetingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel =
            ViewModelProvider(
                this,
                GreetingViewModelFactory(this)
            ).get(GreetingViewModel::class.java)

        val builder: AlertDialog.Builder = GreetingActivity::class.java.let {
            AlertDialog.Builder(this)
        }
        builder.setMessage(viewModel.getGreetingMessage())
            .setTitle(R.string.greeting)
        val dialog: AlertDialog? = builder.create()


        binding.greetingButton.setOnClickListener {
            dialog?.show()
        }

        binding.exit.setOnClickListener {
            viewModel.exit()
            val intent = Intent(this@GreetingActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}