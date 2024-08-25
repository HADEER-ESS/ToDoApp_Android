package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao): ViewModel() {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks().asLiveData()

    fun getTaskByDate(taskDate : String){
        viewModelScope.launch {
            taskDao.getTaskByDate(taskDate)
        }
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