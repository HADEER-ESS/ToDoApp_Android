package com.example.todoapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId : Int,
    @ColumnInfo val taskTitle : String,
    @ColumnInfo val taskDetails : String?,
    @ColumnInfo var isDone : Boolean = false,
    @ColumnInfo val date : String
)
