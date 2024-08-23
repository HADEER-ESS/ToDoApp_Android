package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object{
        const val DATABASE_NAME = "task_db"

        private var taskDataBaseInstance : TaskDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : TaskDatabase{

            if(taskDataBaseInstance == null){
                taskDataBaseInstance = Room.databaseBuilder(
                    context.applicationContext , TaskDatabase::class.java , DATABASE_NAME
                ).build()
            }
            return taskDataBaseInstance!!
        }
    }
}