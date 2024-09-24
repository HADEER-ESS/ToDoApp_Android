package com.example.todoapp

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.todoapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private var TIME_DELAY : Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleAppLocalization()
        CoroutineScope(context = Dispatchers.Main).launch {
            delay(TIME_DELAY)
            val intent = Intent(this@SplashActivity , MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun handleAppLocalization(){
        val currentLanguage = AppCompatDelegate.getApplicationLocales()[0].toString()
        println("application current language is $currentLanguage")
        val localList = LocaleListCompat.forLanguageTags(currentLanguage)
        AppCompatDelegate.setApplicationLocales(localList)
    }
}

//TODO(
// -show tasks based on selected date       =>  (DONE)
// -change selected date container text color on user selection     =>  (DONE)
// -prevent user from select old date (from today)      =>  (DONE)
// -finish Edit Task screen view        =>  (DONE)
// -apply functionality for Edit Tasks Screen       =>  (DONE)
// ) //
