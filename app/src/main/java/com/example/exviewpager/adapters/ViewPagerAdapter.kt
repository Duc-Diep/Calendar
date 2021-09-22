package com.example.exviewpager.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.exviewpager.fragments.MonthFragment
import java.time.LocalDate

class ViewPagerAdapter(fragmentManager: FragmentManager, var fragList: ArrayList<MonthFragment>) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return 3
    }


    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }


    fun setCalendar(currentMonth: LocalDate,value:Int) {
        var prevMonth = currentMonth.minusMonths(1)

        var nextMonth = currentMonth.plusMonths(1)

        fragList[0].updateUI(prevMonth,value)
        fragList[1].updateUI(currentMonth,value)
        fragList[2].updateUI(nextMonth,value)
    }
}