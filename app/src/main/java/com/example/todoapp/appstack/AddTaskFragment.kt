package com.example.todoapp.appstack

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.TaskViewModel
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
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
    private lateinit var dateTextView : TextView
    private val calender = Calendar.getInstance()

    private var taskName : String = ""
    private var taskDetails : String = ""
    private var selectedTaskate : Long = 0

    lateinit private var taskViewModel : TaskViewModel
    //lateinit private var taskDao : TaskDao

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
        dateTextView = binding.selectedDateTv
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRoom()
        binding.addTaskFBtn.setOnClickListener {
            addNewTask()
            dismiss()
        }

        taskTitle.doAfterTextChanged {taskTitleHanlde() }

        taskDescription.doAfterTextChanged { taskDescriptionHandle() }

        taskData.setOnClickListener { taskDateSelection() }
    }

    private fun initializeRoom(){
        //create instance from database
        val taskDatabase = TaskDatabase.getInstance(requireContext())
        val taskDao = taskDatabase.taskDao()
        taskViewModel = TaskViewModel(taskDao)
    }

    private fun addNewTask() {
        //take => task title
        //      => task desc ?= ""
        //      => task date
        //      => task status
        val task = Task(0,taskName , taskDetails , false , selectedTaskate)
        println("task $task")
        taskViewModel.insertTask(task)
//        CoroutineScope(Dispatchers.IO).launch {
//            taskDao?.insertTask(task)
//            println("inserted done")
//        }

    }

    fun taskTitleHanlde(){
        if(taskTitle?.text?.length == 0){
            binding.taskTitleLy.error = "Please Enter Task"
        }
        else{
            binding.taskTitleLy.error = null
            taskName = taskTitle.text.toString()
        }
    }

    fun taskDescriptionHandle(){
        if(taskDescription.text?.length == 0){
            taskDetails = ""
        }
        else{
            taskDetails = taskDescription.text.toString()
        }
    }

    fun taskDateSelection(){
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        // Create a date validator that allows only dates from today onwards
        val dateValidator = DateValidatorPointForward.from(calender.timeInMillis)

        //create Constrain Builder to prevent user from selection old date from calender
        val constraintsBuilder = CalendarConstraints.Builder()
            .setStart(calender.timeInMillis)
            .setValidator(dateValidator)

        val datePickerDialog =  MaterialDatePicker.Builder.datePicker()
            .setTextInputFormat(sdf)
            .setSelection(calender.timeInMillis)
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePickerDialog.addOnPositiveButtonClickListener {
            val date = Date(it)
            val stringDateFormate = sdf.format(date)
            val longFormate = sdf.parse(stringDateFormate)
            selectedTaskate = longFormate.time
//            println("date picker ${selectedTaskate}")
            dateTextView.text = stringDateFormate.toString()
        }
        datePickerDialog.show(parentFragmentManager , "materialDatePicker")
    }
}