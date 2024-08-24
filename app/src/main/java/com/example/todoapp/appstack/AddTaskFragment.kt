package com.example.todoapp.appstack

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Date

class AddTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentAddTaskBinding
    private lateinit var taskTitle : TextInputEditText
    private lateinit var taskDescription : TextInputEditText
    private lateinit var taskData : AppCompatButton

    private var taskName : String = ""
    private var taskDetails : String = ""
    private var selectedTaskate : String = ""

    private var taskDao : TaskDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater , container , false)
        taskTitle = binding.taskTitleInput
        taskDescription = binding.taskDetailsInput
        taskData = binding.selectDateBtn
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRoom()
        binding.addTaskFBtn.setOnClickListener {
            addNewTask()
            dismiss()
        }

        taskTitle.setOnFocusChangeListener { v, hasFocus -> taskTitleHanlde(hasFocus) }

        taskDescription.setOnFocusChangeListener { v, hasFocus -> taskDescriptionHandle(hasFocus) }

        taskData.setOnClickListener { taskDateSelection() }
    }

    private fun initializeRoom(){
        //create instance from database
        val taskDatabase = TaskDatabase.getInstance(requireContext())
        taskDao = taskDatabase.taskDao()
    }

    private fun addNewTask() {
        //take => task title
        //      => task desc ?= ""
        //      => task date
        //      => task status
        val task = Task(0,taskName , taskDetails , false , selectedTaskate)
        println("task $task")
        CoroutineScope(Dispatchers.IO).launch {
            taskDao?.insertTask(task)
            println("inserted done")
        }

    }

    fun taskTitleHanlde(focus : Boolean){
        if(!focus && taskTitle.text.isNullOrBlank()){
            binding.taskTitleLy.error = "Please Enter Task"
        }
        else{
            binding.taskTitleLy.error = null
            taskName = taskTitle.text.toString()
//            println("something $taskName")
        }
    }

    fun taskDescriptionHandle(focus: Boolean){
        if(binding.taskDetailsInput.text.isNullOrBlank()){
            taskDetails = ""
        }
        else{
            taskDetails = taskDescription.text.toString()
        }
    }

    fun taskDateSelection(){
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val datePickerDialog =  MaterialDatePicker.Builder.datePicker()
            .setTextInputFormat(sdf)
            .build()

        datePickerDialog.addOnPositiveButtonClickListener {
            val date = Date(it)
            val formate = sdf.format(date)
            selectedTaskate = formate
            println("date picker ${formate}")
        }
        datePickerDialog.show(parentFragmentManager , "materialDatePicker")
    }
}