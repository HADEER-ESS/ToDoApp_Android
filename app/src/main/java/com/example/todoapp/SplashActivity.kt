package com.example.todoapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.todoapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private var TIME_DELAY : Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(context = Dispatchers.Main).launch {
            delay(TIME_DELAY)
            val intent = Intent(this@SplashActivity , MainActivity::class.java)
            startActivity(intent)
//            findNavController(R.id.nav_graph).navigate(R.id.action_splashActivity_to_homePageFragment)
        }
    }
}

//TODO(
// -show tasks based on selected date       =>  (DONE)
// -change selected date container text color on user selection     =>  (DONE)
// -prevent user from select old date (from today)      =>  (DONE)
// -finish Edit Task screen view
// -apply functionality for Edit Tasks Screen
// ) //
