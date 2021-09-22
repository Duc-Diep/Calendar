package com.example.exviewpager.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.exviewpager.R
import com.example.exviewpager.adapters.CalendarAdapter
import com.example.exviewpager.adapters.DayOfWeekAdapter
import com.example.exviewpager.objects.Day
import com.example.exviewpager.objects.DaysOfWeek
import com.example.exviewpager.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_month.*
import kotlinx.android.synthetic.main.fragment_month.view.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MonthFragment : Fragment() {
    var dayAdapter: CalendarAdapter? = null
    lateinit var selectedDate: LocalDate
    lateinit var daysInMonth: ArrayList<Day>
    private var valueFirstDayOfWeek: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.init(context)
        arguments?.let {
            selectedDate = it.getSerializable("month") as LocalDate
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_month, container, false)
        valueFirstDayOfWeek = AppPreferences.firstDayOfWeek
        //set up when load app
        getDaysInMonth(selectedDate, valueFirstDayOfWeek)
        view.rcv_days.adapter = dayAdapter
        val dividerItemDecoration = DividerItemDecoration(
            context,
            RecyclerView.VERTICAL
        )
        view.rcv_days.addItemDecoration(dividerItemDecoration)

        return view
    }

    //add day to list
    private fun getDaysInMonth(localDate: LocalDate, value: Int) {
        daysInMonth = ArrayList()
        var monthValue = localDate.monthValue
        var yearMonth = YearMonth.from(localDate)
        var lengthOfMonth = yearMonth.lengthOfMonth()
        var firstOfMonth = localDate.withDayOfMonth(1)
        var dayOfWeek = firstOfMonth.dayOfWeek.value + 7
        var numberDayOfPreviousWeek = dayOfWeek
        var previousMonthLength = YearMonth.from(localDate.minusMonths(1)).lengthOfMonth()
        var indexDay = 1
        var countNumberDayOfPreviousWeek = 0
        var currentTime = LocalDate.now()
        for (i in 1..50) {
            if (i < dayOfWeek) {
                daysInMonth.add(
                    Day(
                        previousMonthLength - numberDayOfPreviousWeek + 2,
                        monthValue - 1,
                        false,
                        false
                    )
                )
                numberDayOfPreviousWeek--
                countNumberDayOfPreviousWeek++
            } else if (i >= dayOfWeek && i <= lengthOfMonth + dayOfWeek) {
                if (i == (lengthOfMonth + dayOfWeek)) {
                    indexDay = 1
                } else {
                    if (indexDay == AppPreferences.checkedDay&& monthValue==AppPreferences.checkedMonth) {
                        daysInMonth.add(Day(indexDay, monthValue, true, true))
                    } else {
                        daysInMonth.add(Day(indexDay, monthValue, false, true))
                    }
                    indexDay++
                }

            } else {
                daysInMonth.add(Day(indexDay, monthValue + 1, false, false))
                indexDay++
            }
        }


        //reset list day when fisrt day of week changed
        var count = value
        while (count > 0) {
            daysInMonth.removeAt(0)
            daysInMonth.add(Day(indexDay, monthValue+1, false, false))
            indexDay++
            count--
        }

        if (daysInMonth[6].number > 20) {
            for (i in 1..7) {
                daysInMonth.removeAt(0)
            }
        }
        if (daysInMonth.size == 49) {
            for (i in 1..7) {
                daysInMonth.removeAt(daysInMonth.size - 1)
            }
        }

        dayAdapter = CalendarAdapter(context, daysInMonth)

    }

    //update UI when scroll left or right
    fun updateUI(newMonth: LocalDate, value: Int) {
        valueFirstDayOfWeek = value
        selectedDate = newMonth
        getDaysInMonth(newMonth, valueFirstDayOfWeek)
        view?.rcv_days?.adapter = dayAdapter
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
