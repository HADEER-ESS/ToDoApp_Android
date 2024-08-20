package com.example.todoapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp.appstack.AddTaskFragment
import com.example.todoapp.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.findFragmentById(binding.fragmentIncludeTag.navHostFragmentContainer.id)
        val navController = fragment?.findNavController()

        storeApplicationSettingDefaults()
        binding.fabAddTask.setOnClickListener {
            handleAddTaskView()
        }



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

                else -> {
                    false
                }

            }
        }
    }

    fun storeApplicationSettingDefaults(){

        val appMode = (
                resources.configuration.uiMode and
                        android.content.res.Configuration.UI_MODE_NIGHT_MASK
                ) == android.content.res.Configuration.UI_MODE_NIGHT_YES

        //appMode === false => Light mode
        //appMode === true => dark mode

        val sharedPreferences = getSharedPreferences("todoApp" , MODE_PRIVATE)
        var appEditor : SharedPreferences.Editor;

        if(!sharedPreferences.contains("lang")){
            appEditor = sharedPreferences.edit()
            appEditor.putString("lang" , Locale.getDefault().language)
            appEditor.apply()
        }else{
            val lang = sharedPreferences.all["lang"].toString()
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
        }
        if(!sharedPreferences.contains("mode")){
            appEditor = sharedPreferences.edit()
            appEditor.putBoolean("mode" , appMode)
            appEditor.apply()
        }
        else{
            val mode = sharedPreferences.all["mode"].toString().toBoolean()
            if(mode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

    }

    fun handleAddTaskView(){
        val bottomSheetFragment = AddTaskFragment()
        bottomSheetFragment.show(supportFragmentManager , bottomSheetFragment.tag)
    }
}