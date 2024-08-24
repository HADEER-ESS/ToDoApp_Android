package com.example.todoapp.appstack

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.Task
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.TaskViewModel
import com.example.todoapp.databinding.FragmentHomePageBinding
import com.google.android.material.datepicker.DayViewDecorator
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekCalendarView
import com.kizitonwose.calendar.view.WeekDayBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


class HomePageFragment : Fragment() , OnItemClicklisnter {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var weekCalenderView : WeekCalendarView
    private lateinit var taskViewModel: TaskViewModel

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksShowAdaptor: TasksShowAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater , container , false)
        weekCalenderView = binding.taskCalenderView
        tasksRecyclerView = binding.tasksRecyclerView
        tasksShowAdaptor = TasksShowAdaptor(this)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeFragment()
        calenderViewSetup()
    }

    private fun initializeFragment() {
        //create instance from database
        val taskDatabase = TaskDatabase.getInstance(requireContext())
        val taskDao = taskDatabase.taskDao()
        taskViewModel = TaskViewModel(taskDao)
        //link recycler view with adaptor
        tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //get all tasks from database
        applyDataToScreen()
    }

    private fun applyDataToScreen() {
        taskViewModel.allTasks.observe(viewLifecycleOwner){
            tasks -> tasksShowAdaptor.submitList(tasks)
        }
        tasksRecyclerView.adapter = tasksShowAdaptor
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calenderViewSetup(){
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = LocalDate.now().dayOfWeek // Today (currend day) as a day of week
        weekCalenderView.setup(startDate, endDate , firstDayOfWeek)
        weekCalenderView.scrollToWeek(LocalDate.now())
        createCalenderBinding()
    }

    private fun createCalenderBinding(){
        val blueColor = ContextCompat.getColor(requireContext(), R.color.main_color)
        val blackColor = ContextCompat.getColor(requireContext() , R.color.black)
        //calender adaptor
        weekCalenderView.dayBinder =
            object : WeekDayBinder<DayViewContainer> {
                @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: WeekDay) {
                val dayText = data.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault()
                )
                val dayNumber = data.date.dayOfMonth.toString()
                println("data.date = ${data} , text ${dayText} , dayNumber $dayNumber")
                container.dayTitleView.text =  dayText
                container.dayNumberView.text = dayNumber

                //if date is today
                if(data.date == LocalDate.now()){
                    container.dayTitleView.setTextColor(blueColor)
                    container.dayNumberView.setTextColor(blueColor)
                }
                else{
                    container.dayTitleView.setTextColor(blackColor)
                    container.dayNumberView.setTextColor(blackColor)
                }
            }

            override fun create(view: View): DayViewContainer {
                return  DayViewContainer(view)
            }

            }


    }

    override fun updateTaskComplete(data: Task) {
        taskViewModel.updateTaskInfo(data)
    }

    override fun deleteTask(data: Task) {
        taskViewModel.deleteTask(data)
    }
}