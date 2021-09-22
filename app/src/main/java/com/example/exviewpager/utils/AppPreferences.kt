package com.example.exviewpager.utils

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate
import java.time.LocalDateTime

object AppPreferences {
    private const val NAME = "AppPreferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val FIRST_DAYS_OF_WEEK = Pair("first_day_of_week", 0)
    private val CHECKED_DAY = Pair("checked_day", LocalDate.now().dayOfMonth)
    private val CHECKED_MONTH = Pair("checked_month", LocalDate.now().monthValue)

    fun init(context: Context?) {
        preferences = context?.getSharedPreferences(NAME, MODE)!!
    }
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit() //SharedPreferences.Editor editor
        operation(editor)
        editor.apply()
    }

    var firstDayOfWeek: Int
        get() = preferences.getInt(FIRST_DAYS_OF_WEEK.first, FIRST_DAYS_OF_WEEK.second)
        set(value) = preferences.edit {
            it.putInt(FIRST_DAYS_OF_WEEK.first, value)
        }
    var checkedDay: Int
        get() = preferences.getInt(CHECKED_DAY.first, CHECKED_DAY.second)
        set(value) = preferences.edit {
            it.putInt(CHECKED_DAY.first, value)
        }
    var checkedMonth: Int
        get() = preferences.getInt(CHECKED_MONTH.first, CHECKED_MONTH.second)
        set(value) = preferences.edit {
            it.putInt(CHECKED_MONTH.first, value)
        }
}