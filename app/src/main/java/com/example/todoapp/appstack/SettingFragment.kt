package com.example.todoapp.appstack

import android.content.Context
import android.content.Context.MODE_APPEND
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    private lateinit var binding :FragmentSettingBinding
    private lateinit var storedData :SharedPreferences
    private lateinit var languagesSpinner : Spinner
    private lateinit var modeSpinner : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //shared preferance data
        storedData = requireActivity().getSharedPreferences("todoApp" , Context.MODE_PRIVATE)

         languagesSpinner = binding.languageSelectedSpinner
         modeSpinner = binding.modeSelectedSpinner

        handleLanguageSpinner()
        handleModeSpinner()
    }


    fun handleLanguageSpinner(){
        val defaultLanguage = storedData.all["lang"] //English
        println("default language is $defaultLanguage")//en
        val spinnerLanguageData = resources.getStringArray(R.array.language_array)

        // Create an ArrayAdapter using the string array and a default spinner layout FOR LANGUAGE
        val languageAdapter = ArrayAdapter(requireContext(), R.layout.custome_spinner_item, spinnerLanguageData)
        languageAdapter.setDropDownViewResource(R.layout.custome_spinner_dropdown_item)

        languagesSpinner.adapter = languageAdapter
        if(defaultLanguage == "en"){
            languagesSpinner.setSelection(0)
        }else{
            languagesSpinner.setSelection(1)
        }

        languagesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                languagesSpinner.setSelection(position)
                if(spinnerLanguageData[position] == "English" || spinnerLanguageData[position] == "الانجليزية"){
                    val editor = storedData.edit()
                    editor.putString("lang" , "en")
                    editor.apply()
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                }else{
                    val editor = storedData.edit()
                    editor.putString("lang" , "ar")
                    editor.apply()
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun handleModeSpinner(){
        val defaultMode = storedData.all["mode"]
        println("default mode is $defaultMode")//false
        val spinnerModeData = resources.getStringArray(R.array.Mode_array)

        //create Mode Selector Spinner

        val modeAdapter = ArrayAdapter(requireContext(), R.layout.custome_spinner_item, spinnerModeData)
        modeAdapter.setDropDownViewResource(R.layout.custome_spinner_dropdown_item)

        modeSpinner.adapter = modeAdapter
        if(defaultMode == true){
            modeSpinner.setSelection(1)
        }else{
            modeSpinner.setSelection(0)
        }

        modeSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                println("inside lisnter " + spinnerModeData[position])
                modeSpinner.setSelection(position)
                if(spinnerModeData[position] == "Light" || spinnerModeData[position] == "نهارى"){
                    val editor = storedData.edit()
                    editor.putBoolean("mode" , false)
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }else{
                    val editor = storedData.edit()
                    editor.putBoolean("mode" , true)
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}