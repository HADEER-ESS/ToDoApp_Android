package com.example.todoapp


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp.appstack.AddTaskFragment
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(binding.fragmentIncludeTag.navHostFragmentContainer.id)
        navController = fragment?.findNavController()!!
        handleBottomTabsNavigation()

        handleAppLocalization()
        binding.fabAddTask.setOnClickListener {
            handleAddTaskView()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        handleBottomTabApparance()
    }

    private fun handleBottomTabApparance() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.editScreenFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun handleBottomTabsNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homePageFragment -> {
                    if(navController?.currentDestination?.id != R.id.homePageFragment){
                        navController?.navigate(R.id.homePageFragment)
                        val appTitle = resources.getString(R.string.app_title)
                        binding.mainTitleTv.text = appTitle
                    }
                    true
                }

                R.id.settingFragment -> {
                    if (navController?.currentDestination?.id != R.id.settingFragment){
                        navController?.navigate(R.id.settingFragment)
                        val appTitle = resources.getString(R.string.setting)
                        binding.mainTitleTv.text = appTitle
                    }
                    true
                }
                else -> false
            }
//            return super.onOptionsItemSelected(menuItem.itemId)
        }
    }

    private fun handleAppLocalization(){
        val currentLanguage = AppCompatDelegate.getApplicationLocales()[0].toString()
        println("application current language is $currentLanguage")
        val localList = LocaleListCompat.forLanguageTags(currentLanguage)
        AppCompatDelegate.setApplicationLocales(localList)
    }

    private fun handleAddTaskView(){
        val bottomSheetFragment = AddTaskFragment()
        bottomSheetFragment.show(supportFragmentManager , bottomSheetFragment.tag)
    }
}