package com.example.exviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.exviewpager.adapters.ViewPagerAdapter
import com.example.exviewpager.fragments.MonthFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

private const val PAGE_CENTER = 1

class MainActivity : AppCompatActivity() {
    lateinit var localDate: LocalDate
    lateinit var fragList: ArrayList<MonthFragment>
    lateinit var pageAdapter: ViewPagerAdapter
    var focusPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        localDate = LocalDate.now()
        //setup ViewPager
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
                        Toast.makeText(this@MainActivity,"Scroll Left $focusPage $localDate",Toast.LENGTH_SHORT).show()
                    } else if (focusPage > PAGE_CENTER) {
                        localDate = localDate.plusMonths(1)
                        Toast.makeText(this@MainActivity,"Scroll Right $focusPage $localDate",Toast.LENGTH_SHORT).show()

                    }
                    pageAdapter.setCalendar(localDate)
                    view_pager.setCurrentItem(1, false)
                }
            }

        })

        //custome ViewPager
//        view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        view_pager.setPageTransformer(ZoomOutPageTransformer())

    }
}