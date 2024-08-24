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
        val startDate = LocalDate.now()// Adjust as needed
        val endDate = startDate.plusDays(30) // Adjust as needed
        val firstDayOfWeek = LocalDate.now().dayOfWeek // Today (currend day) as a day of week
        weekCalenderView.setup(startDate, endDate , firstDayOfWeek)
        weekCalenderView.scrollToDate(LocalDate.now())
        createCalenderBinding()
    }

    private fun createCalenderBinding(){
        //calender adaptor
        weekCalenderView.dayBinder =
            object : WeekDayBinder<DayViewContainer> {
                @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: WeekDay) {
                val dayText = data.date.dayOfWeek.name.take(1)
                val dayNumber = data.date.dayOfMonth.toString()
                println("data.date = ${data} , text ${dayText} , dayNumber $dayNumber")
                container.dayTitleView.text =  dayText
                container.dayNumberView.text = dayNumber

                //if date is today
                if(data.date == LocalDate.now()){
                    container.dayContainer.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
                }
                else{
                    container.dayContainer.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }

            override fun create(view: View): DayViewContainer {
                return  DayViewContainer(view)
            }

            }


    }

    override fun onClickLisnter(data: Task) {
        taskViewModel.updateTaskInfo(data)
    }

}