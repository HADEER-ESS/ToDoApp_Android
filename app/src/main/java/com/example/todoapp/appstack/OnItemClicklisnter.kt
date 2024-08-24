package com.example.todoapp.appstack

import android.view.View
import com.example.todoapp.database.Task

interface OnItemClicklisnter {
    fun updateTaskComplete(data : Task)
    fun deleteTask(data : Task)
}