package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    suspend fun getAllTasks() : List<Task>

    @Query("SELECT * FROM Task WHERE taskId LIKE :id")
    suspend fun getTaskById(id : Int) : Task

    @Insert
    suspend fun insertTask(vararg task : Task)

    @Update
    suspend fun updateTaskInfo(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)
}