package com.example.todoapp

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(binding.fragmentIncludeTag.navHostFragmentContainer.id)
        val navController = fragment?.findNavController()


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homePageFragment -> {
                    if(navController?.currentDestination?.id != R.id.homePageFragment){
                        navController?.navigate(R.id.homePageFragment)
                        binding.mainTitleTv.text = "To Do List"
                    }
                    true
                }

                R.id.settingFragment -> {
                    if (navController?.currentDestination?.id != R.id.settingFragment){
                        navController?.navigate(R.id.settingFragment)
                        binding.mainTitleTv.text = "Settings"
                    }
                    true
                }

                else -> {
                    false
                }

            }
        }
    }
}