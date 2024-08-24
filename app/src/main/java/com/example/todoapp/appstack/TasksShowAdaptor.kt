package com.example.todoapp.appstack

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskViewModel
import com.example.todoapp.databinding.TaskItemViewBinding

class TasksShowAdaptor(private val lisnter : OnItemClicklisnter): ListAdapter<Task, TasksShowAdaptor.TasksViewHolder>(TaskDiffCallback()) {

    class TasksViewHolder(val binding : TaskItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        val taskCard = binding.taskItemCard
        val taskTitle = binding.taskTitleTv
        val taskDate = binding.taskDateTv
        val tasksideMark = binding.cardMarkView
        val checkbtn = binding.taskDoneBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return TasksViewHolder(TaskItemViewBinding.inflate(inflater , parent , false))
    }



    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        val context = holder.itemView.context
        val greenColor = ContextCompat.getColor(context , R.color.done_green)
        val blueColor = ContextCompat.getColor(context , R.color.main_color)

        holder.taskTitle.text = task.taskTitle
        holder.taskDate.text = task.date

        holder.checkbtn.setOnClickListener { updateDataCheck( position) }

        if(task.isDone){
            holder.tasksideMark.setBackgroundResource(R.color.done_green)

            holder.taskTitle.setTextColor(greenColor)
            holder.checkbtn.setBackgroundResource(R.color.transparent)
            holder.checkbtn.text = context.getString(R.string.done_txt)
            holder.checkbtn.setTextSize(22f)
            holder.checkbtn.setTextColor(greenColor)
        }
        else{
            holder.tasksideMark.setBackgroundResource(R.color.main_color)
            holder.taskTitle.setTextColor(blueColor)
        }
    }

    fun updateDataCheck(position: Int){
        val task = getItem(position)
        task.isDone = !task.isDone

        lisnter.onClickLisnter(task)
        notifyItemChanged(position)
    }
}

// DiffUtil Callback
class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId  // Compare unique IDs
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem  // Compare entire content
    }
}