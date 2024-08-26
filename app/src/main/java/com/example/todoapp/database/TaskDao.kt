package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import java.time.LocalDate

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAllTasks() : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE date LIKE :taskDate")
    fun getTaskByDate(taskDate : Long) : Flow<List<Task>>

    @Insert
    suspend fun insertTask(vararg task : Task)

    @Update
    suspend fun updateTaskInfo(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)
}