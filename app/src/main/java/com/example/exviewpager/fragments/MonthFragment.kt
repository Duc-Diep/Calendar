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
    lateinit var selectedDate:LocalDate
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
//        var layout = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//        view.day_of_week.layoutManager = layout
        return view
    }

    private fun setMonthView(localDate: LocalDate):String {
        var formatter  = DateTimeFormatter.ofPattern("MMMM yyyy")
        return localDate.format(formatter)
    }

    private fun getDaysInMonth(localDate: LocalDate){
        daysInMonth = ArrayList()
        var yearMonth = YearMonth.from(localDate)
        var lengthOfMonth = yearMonth.lengthOfMonth()
        var firstOfMonth = localDate.withDayOfMonth(1)
        var dayOfWeek = firstOfMonth.dayOfWeek.value
        Log.d("TAG", "getDaysInMonth: firstOfMonth: $firstOfMonth, dayOfWeek: $dayOfWeek")
        var indexDay = 1
        for(i in 1..42 ){
            if (i<dayOfWeek||i>lengthOfMonth+dayOfWeek-1){
                daysInMonth.add(Day(0,false))

            }else{
                if (indexDay==1){
                    daysInMonth.add(Day(indexDay,true))
                    indexDay++
                }else{
                    daysInMonth.add(Day(indexDay,false))
                    indexDay++
                }

            }
        }
    }

    fun updateUI(newMonth: LocalDate){
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
