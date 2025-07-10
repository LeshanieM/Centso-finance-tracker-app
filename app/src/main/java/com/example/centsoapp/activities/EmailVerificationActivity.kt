package com.example.centsoapp.activities
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.centsoapp.MainActivity
import com.example.centsoapp.databinding.ActivityEmailVerificationBinding
import com.example.centsoapp.utils.SharedPrefsHelper

class EmailVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVerify.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                SharedPrefsHelper.saveEmail(this, email)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                binding.etEmail.error = "Enter a valid email"
            }
        }
    }
}