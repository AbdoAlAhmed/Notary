package com.theideal.notary.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import com.theideal.notary.R
import com.theideal.notary.auth.AuthenticationActivity
import com.theideal.notary.auth.AuthenticationViewModel
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val authenticationViewModel by viewModel<AuthenticationViewModel>()
    private var isSignIn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)
        authenticationViewModel.isUserLoggedIn()
        authenticationViewModel.isUserLoggedIN.observe(this) {
            isSignIn = it
        }
        val handlerThread = HandlerThread("SplashScreenHandlerThread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.postDelayed({
            // Start your app main activity
            if (isSignIn) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }
            // close this activity
            finish()
        }, 3800)
    }
}