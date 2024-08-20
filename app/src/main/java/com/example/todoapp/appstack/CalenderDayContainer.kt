package com.example.todoapp.appstack

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.dynamicfeatures.fragment.R
import com.kizitonwose.calendar.view.ViewContainer

class CalenderDayContainer(private val dayViewContainer: View) : ViewContainer(dayViewContainer) {

    val dayContainerName : TextView = dayViewContainer.findViewById(R.id.day_text_tv)
    val dayContainerValue : TextView = dayViewContainer.findViewById(R.id.day_number_tv)
}