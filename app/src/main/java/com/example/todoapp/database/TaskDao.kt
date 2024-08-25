package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAllTasks() : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE date LIKE :taskDate")
    suspend fun getTaskByDate(taskDate : String) : Task

    @Insert
    suspend fun insertTask(vararg task : Task)

    @Update
    suspend fun updateTaskInfo(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)
}