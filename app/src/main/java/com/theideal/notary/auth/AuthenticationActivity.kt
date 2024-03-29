package com.theideal.notary.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.theideal.data.model.User
import com.theideal.notary.databinding.ActivityAuthentactionBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : AppCompatActivity() {
    private val authenticationViewModel by viewModel<AuthenticationViewModel>()
    private lateinit var binding: ActivityAuthentactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthentactionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        authenticationViewModel.isUserLoggedIn()
        authenticationViewModel.isUserLoggedIN.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        setContentView(binding.root)

    }

}