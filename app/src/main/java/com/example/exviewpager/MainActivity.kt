package com.example.exviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.exviewpager.adapters.DayOfWeekAdapter
import com.example.exviewpager.adapters.ViewPagerAdapter
import com.example.exviewpager.fragments.MonthFragment
import com.example.exviewpager.objects.DaysOfWeek
import com.example.exviewpager.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_month.*
import kotlinx.android.synthetic.main.fragment_month.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val PAGE_CENTER = 1

class MainActivity : AppCompatActivity() {
    lateinit var localDate: LocalDate
    lateinit var fragList: ArrayList<MonthFragment>
    lateinit var pageAdapter: ViewPagerAdapter
    lateinit var listDayOfWeek: ArrayList<DaysOfWeek>
    var valueFirstDayOfWeek = 0
    var daysOfWeekAdapter: DayOfWeekAdapter? = null

    var focusPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        reloadData()
        localDate = LocalDate.now()
        setMonthView(localDate)
        //setup ViewPager
        setupViewPager()
        //setup day of week
        setUpDaysOfWeek(valueFirstDayOfWeek)
    }

    private fun setupViewPager() {
        fragList = ArrayList()

        fragList.apply {
            add(MonthFragment.newInstance(localDate.minusMonths(1)))
            add(MonthFragment.newInstance(localDate))
            add(MonthFragment.newInstance(localDate.plusMonths(1)))
        }
        pageAdapter = ViewPagerAdapter(supportFragmentManager, fragList)
        view_pager.adapter = pageAdapter
        view_pager.setCurrentItem(1, false)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                focusPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (focusPage < PAGE_CENTER) {
                        localDate = localDate.minusMonths(1)
                    } else if (focusPage > PAGE_CENTER) {
                        localDate = localDate.plusMonths(1)
                    }
                    setMonthView(localDate)
                    pageAdapter.setCalendar(localDate, valueFirstDayOfWeek)
                    view_pager.setCurrentItem(1, false)
                }
            }

        })
    }


    //format day
    private fun setMonthView(localDate: LocalDate) {
        var formatter = DateTimeFormatter.ofPattern("MM - yyyy")
        tv_month.text = "Tháng ${localDate.format(formatter)}"
    }

    private fun setUpDaysOfWeek(firstValue: Int) {
        listDayOfWeek = ArrayList()
        var index = firstValue
        for (i in 0..6) {
            if (index == 6) {
                addDays(index)
                index = 0
            } else {
                addDays(index)
                index++
            }

        }
        daysOfWeekAdapter = DayOfWeekAdapter(this, listDayOfWeek)
        daysOfWeekAdapter!!.setOnItemClick {
            setUpDaysOfWeek(it)
            AppPreferences.firstDayOfWeek = it
            valueFirstDayOfWeek = it
            pageAdapter.setCalendar(localDate, it)
        }
        rcv_days_of_week?.adapter = daysOfWeekAdapter

    }

    private fun addDays(value: Int) {
        when (value) {
            0 -> listDayOfWeek.add(DaysOfWeek("MON", 0))
            1 -> listDayOfWeek.add(DaysOfWeek("TUE", 1))
            2 -> listDayOfWeek.add(DaysOfWeek("WED", 2))
            3 -> listDayOfWeek.add(DaysOfWeek("THU", 3))
            4 -> listDayOfWeek.add(DaysOfWeek("FRI", 4))
            5 -> listDayOfWeek.add(DaysOfWeek("SAT", 5))
            6 -> listDayOfWeek.add(DaysOfWeek("SUN", 6))
        }
    }

    private fun reloadData(){
        AppPreferences.init(this)
        AppPreferences.firstDayOfWeek = 0
        AppPreferences.checkedDay = LocalDate.now().dayOfMonth
        AppPreferences.checkedMonth = LocalDate.now().monthValue
    }
}