package com.example.todoapp.appstack

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.todoapp.R
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(val dayCardView : View) : ViewContainer(dayCardView) {

    val dayContainer = dayCardView.findViewById<CardView>(R.id.day_container)
    val dayTitleView = dayCardView.findViewById<TextView>(R.id.day_text_tv)
    val dayNumberView = dayCardView.findViewById<TextView>(R.id.day_number_tv)
}