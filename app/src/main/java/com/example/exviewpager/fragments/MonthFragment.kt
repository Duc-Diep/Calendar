package com.example.exviewpager.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exviewpager.objects.Day
import com.example.exviewpager.R
import com.example.exviewpager.adapters.CalendarAdapter
import kotlinx.android.synthetic.main.fragment_month.view.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MonthFragment : Fragment() {
    var dayAdapter: CalendarAdapter? = null
    lateinit var selectedDate: LocalDate
    lateinit var daysInMonth: ArrayList<Day>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDate = it.getSerializable("month") as LocalDate
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_month, container, false)
        view.tv_month.text = setMonthView(selectedDate)
        getDaysInMonth(selectedDate)
        dayAdapter = CalendarAdapter(context, daysInMonth)
        view.rcv_days.adapter = dayAdapter
        return view
    }

    private fun setMonthView(localDate: LocalDate): String {
        var formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return localDate.format(formatter)
    }

    private fun getDaysInMonth(localDate: LocalDate) {
        daysInMonth = ArrayList()
        var yearMonth = YearMonth.from(localDate)
        var lengthOfMonth = yearMonth.lengthOfMonth()
        var firstOfMonth = localDate.withDayOfMonth(1)
        var dayOfWeek = firstOfMonth.dayOfWeek.value
        var numberDayOfPreviousWeek = dayOfWeek
        var previousMonthLength = YearMonth.from(localDate.minusMonths(1)).lengthOfMonth()
        Log.d("TAG", "getDaysInMonth: firstOfMonth: $firstOfMonth, dayOfWeek: $dayOfWeek")
        var indexDay = 1
        for (i in 1..43) {
            if (i < dayOfWeek) {
                daysInMonth.add(
                    Day(
                        previousMonthLength - numberDayOfPreviousWeek + 2,
                        false,
                        false
                    )
                )
                numberDayOfPreviousWeek--
            } else if (i >= dayOfWeek && i <= lengthOfMonth + dayOfWeek) {
                if (i == (lengthOfMonth + dayOfWeek)) {
                    indexDay = 1
                } else {
                    daysInMonth.add(Day(indexDay, false, true))
                    indexDay++
                }

            } else {
                daysInMonth.add(Day(indexDay, false, false))
                indexDay++
            }

        }
    }


    fun updateUI(newMonth: LocalDate) {
        var v = view
        v?.tv_month?.text = setMonthView(newMonth)
        getDaysInMonth(newMonth)
        dayAdapter = CalendarAdapter(context, daysInMonth)
        v?.rcv_days?.adapter = dayAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(month: LocalDate) =
            MonthFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("month", month)
                }
            }
    }

}
