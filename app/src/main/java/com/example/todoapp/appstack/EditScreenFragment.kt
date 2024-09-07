package com.example.todoapp.appstack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.TaskViewModel
import com.example.todoapp.databinding.FragmentEditScreenBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditScreenFragment : Fragment() {
    private lateinit var binding: FragmentEditScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private val todayDate = Calendar.getInstance()

    private var taskId : Int = 0
    private var taskTitle : String = ""
    private var taskDetails : String? = ""
    private var taskDate : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewModelLink()
        initalizeFragment()

//        Task Title Click Listener
        binding.editTaskTitleEdt.doAfterTextChanged {  updateTaskTitle()}
//        binding.editTaskTitleEdt.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//            updateTaskTitle(hasFocus)
//        }

//        Task Details Click Listener
        binding.editTaskDetailsEdt.doAfterTextChanged { updateTaskDetails() }
//        binding.editTaskDetailsEdt.onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
//            updateTaskDetails(hasFocus)
//        }

//        Task Date Click Listener
        binding.editTaskDateBtn.setOnClickListener { resetTaskDate() }

//        save button click action
        binding.confirmTaskChangeBtn.setOnClickListener { updateTaskData()}
    }

    private fun updateTaskTitle(){
        taskTitle = binding.editTaskTitleEdt.text.toString()
    }

    private fun updateTaskDetails() {
        taskDetails =  binding.editTaskDetailsEdt.text.toString()
    }

    private fun resetTaskDate() {
        //set target formate
        val formate = SimpleDateFormat("dd/MM/yyyy")

//        Date validation
        val dateValidation = DateValidatorPointForward.from(todayDate.timeInMillis)

//        create constrain to apply customized validator
        val constrainCalender = CalendarConstraints.Builder()
            .setStart(todayDate.timeInMillis)
            .setValidator(dateValidation)

//        get ready for the date picker dialog
        val datePickerDialog = MaterialDatePicker.Builder.datePicker()
            .setTextInputFormat(formate)
            .setSelection(taskDate)
            .setCalendarConstraints(constrainCalender.build())
            .build()

        //CREATE A LISTENER TO STORE THE NEW DATE
        datePickerDialog.addOnPositiveButtonClickListener {
            taskDate = onSelectedNewDateValue(formate , it)!!
        }

        datePickerDialog.show(parentFragmentManager , "materialDatePicker")
    }

    private fun onSelectedNewDateValue(formate: SimpleDateFormat, selectedDate: Long): Long? {
        val date = Date(selectedDate)
        val formatStringDate = formate.format(date)

        binding.selectedDateTv.text = formatStringDate

        val parseString = formate.parse(formatStringDate)
        return parseString.time;
    }

    private fun updateTaskData() {
//        println("task new Title $taskTitle")
//        println("task new details $taskDetails")
//        println("task new Date $taskDate")
        taskViewModel.updateTaskInfo(Task(taskId , taskTitle , taskDetails , false , taskDate))
        Toast.makeText(requireContext(), "Task update successfully!" , Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_editScreenFragment_to_homePageFragment)
    }

    private fun initalizeFragment() {
        val incomeTaskData: Task
        arguments?.let {
            incomeTaskData = it?.getParcelable("task_data")!!
            taskTitle = incomeTaskData.taskTitle
            taskDetails = incomeTaskData.taskDetails
            taskDate = incomeTaskData.date
            taskId = incomeTaskData.taskId
        }

        binding.editTaskTitleEdt.setText(taskTitle , TextView.BufferType.EDITABLE)

        binding.editTaskDetailsEdt.setText(taskDetails, TextView.BufferType.EDITABLE)

        val date = Date(taskDate)
        val stringFormDate = SimpleDateFormat("dd/MM/yyyy" , Locale.getDefault()).format(date)
        binding.selectedDateTv.text = stringFormDate
    }

    private fun handleViewModelLink() {
        //create instance of task data
        val taskDataBase = TaskDatabase.getInstance(requireContext())
        val taskDao = taskDataBase.taskDao()
        taskViewModel = TaskViewModel(taskDao)
    }
}