package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.time.LocalDate

class TaskViewModel(private val taskDao: TaskDao): ViewModel() {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks().asLiveData()


    fun getTaskByDate(taskDate : Long) : LiveData<List<Task>>? {
        println("view model income date $taskDate")
        var tasksData : LiveData<List<Task>>? = null
        viewModelScope.launch {
            tasksData = taskDao.getTaskByDate(taskDate).asLiveData()
        }
        println("view model out data $tasksData")
        return tasksData
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun updateTaskInfo(task: Task){
        viewModelScope.launch {
            taskDao.updateTaskInfo(task)
        }
    }
}