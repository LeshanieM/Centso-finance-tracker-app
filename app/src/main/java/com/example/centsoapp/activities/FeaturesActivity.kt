package com.example.centsoapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.centsoapp.databinding.ActivityFeaturesBinding

class FeaturesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeaturesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeaturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, EmailVerificationActivity::class.java))
            finish()
        }
    }
}