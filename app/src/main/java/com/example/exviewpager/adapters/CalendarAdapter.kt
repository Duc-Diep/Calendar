package com.example.exviewpager.adapters

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exviewpager.objects.Day
import com.example.exviewpager.R
import com.example.exviewpager.events.DoubleClickListener

class CalendarAdapter(var context: Context?, var dayOfMonth:ArrayList<Day>) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {
    var index = -1
    var color: Int = Color.YELLOW
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.day_item,parent,false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        var day = dayOfMonth[position]
        holder.tvDay.text = day.number.toString()
        if (day.checked){
            index = position
            holder.bgrChecked.setBackgroundColor(color)
        }
        holder.itemView.setOnClickListener(object: DoubleClickListener() {
            override fun onDoubleClick() {
                dayOfMonth[index].checked = false
                dayOfMonth[position].checked = true
                color = Color.RED
                notifyDataSetChanged()
            }

            override fun onSingleClick() {
                dayOfMonth[index].checked = false
                dayOfMonth[position].checked = true
                color = Color.YELLOW
                notifyDataSetChanged()
            }

        })

    }

    override fun getItemCount(): Int {
        return dayOfMonth.size
    }
    class DayViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var tvDay:TextView = itemView.findViewById(R.id.tv_day_item)
        var bgrChecked:RelativeLayout = itemView.findViewById(R.id.bgr_day_checked)
    }
}