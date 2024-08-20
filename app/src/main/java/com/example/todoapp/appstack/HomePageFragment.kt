package com.example.todoapp.appstack

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomePageBinding
import com.google.android.material.datepicker.DayViewDecorator
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.YearMonth


class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater , container , false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calenderViewSetup()

//        binding.taskCalenderView.dayBinder = object : MonthDayBinder<CalenderDayItem>{
//            override fun bind(container: CalenderDayItem, data: CalendarDay) {
//                TODO("Not yet implemented")
//            }
//
//            override fun create(view: View): CalenderDayItem {
//                TODO("Not yet implemented")
//            }
//
//        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calenderViewSetup(){
        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        binding.taskCalenderView.setup(startDate, endDate, firstDayOfWeek)
        binding.taskCalenderView.scrollToWeek(currentDate)
        createCalenderBinding()
    }

    fun createCalenderBinding(){

//        binding.taskCalenderView.dayBinder = object : MonthDayBinder<CalenderDayContainer>{
//            override fun bind(container: CalenderDayContainer, data: CalendarDay) {
//                container.dayContainerName.text = data.date.dayOfWeek.toString().substring(0,3)
//                container.dayContainerValue.text = data.date.dayOfMonth.toString()
//            }

//            override fun create(view: View): CalenderDayContainer {
//                return CalenderDayContainer(view)
//            }

        }
    }
}