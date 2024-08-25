package com.example.todoapp.appstack


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.Task
import com.example.todoapp.databinding.TaskItemViewBinding

class TasksShowAdaptor(
    private val lisnter : OnItemClicklisnter,
    private val onNavigate : (Task) -> Unit
): ListAdapter<Task, TasksShowAdaptor.TasksViewHolder>(TaskDiffCallback()) {

    class TasksViewHolder(binding : TaskItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        val taskCardItem : CardView = binding.dragItem
        val taskTitle = binding.taskTitleTv
        val taskDate = binding.taskDateTv
        val tasksideMark = binding.cardMarkView
        val checkbtn = binding.taskDoneBtn
        val deleteTaskBtnImage = binding.leftView
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
        holder.deleteTaskBtnImage.setOnClickListener { deleteTaskItem(task) }
        holder.taskCardItem.setOnClickListener {
            onNavigate(task)
        }

        if(task.isDone){
            holder.tasksideMark.setBackgroundResource(R.color.done_green)

            holder.taskTitle.setTextColor(greenColor)
            holder.checkbtn.setBackgroundResource(R.color.transparent)
            holder.checkbtn.text = context.getString(R.string.done_txt)
            holder.checkbtn.textSize = 22f
            holder.checkbtn.setTextColor(greenColor)
        }
        else{
            holder.tasksideMark.setBackgroundResource(R.color.main_color)
            holder.taskTitle.setTextColor(blueColor)
        }
    }

    private fun updateDataCheck(position: Int){
        val task = getItem(position)
        task.isDone = !task.isDone

        lisnter.updateTaskComplete(task)
        notifyItemChanged(position)
    }

    private fun deleteTaskItem(task : Task){
        lisnter.deleteTask(task)
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