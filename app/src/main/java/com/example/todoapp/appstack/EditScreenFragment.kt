package com.example.todoapp.appstack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.TaskViewModel
import com.example.todoapp.databinding.FragmentEditScreenBinding

class EditScreenFragment : Fragment() {
    private lateinit var binding: FragmentEditScreenBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var incomeTaskData : Task

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

        initalizeFragment()
    }

    private fun initalizeFragment() {
        //create instance of task data
        val taskDataBase = TaskDatabase.getInstance(requireContext())
        val taskDao = taskDataBase.taskDao()
        taskViewModel = TaskViewModel(taskDao)
//        arguments?.let {
//            incomeTaskData = it?.getParcelable("task_data")!!
//        }
//        println("income parceable data $incomeTaskData")
    }
}