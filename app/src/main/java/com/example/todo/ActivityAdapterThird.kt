package com.example.todo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.list_activity_item.view.*

class ActivityAdapterThird(): BaseAdapter() {
    lateinit var context: Context
    lateinit var activities: ArrayList<Activity>

    constructor(context: Context, activities: ArrayList<Activity>):this(){
        this.context=context
        this.activities=activities
    }

    override fun getCount(): Int {
        return activities.size
    }

    override fun getItem(position: Int): Any {
        return activities[position]
    }

    override fun getItemId(position: Int): Long {
        return activities[position].activityID.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = layoutInflater.inflate(R.layout.list_activity_item, null, true)
        val activityTextView=itemView.activity_item_text as TextView
        val activityTextID=itemView.activity_item_id as TextView
        val activityHour=itemView.activity_item_hour as TextView
        val imageDeleteTrash=itemView.activity_trash_id as ImageView
        activityTextView.text=getTitle(position).toString()
        activityTextID.text=getItemId(position).toString()
        activityHour.text=getHour(position).toString()
        imageDeleteTrash.setTag("button_"+getItemId(position).toString())
        //Log.i("CreatedTag", "button_"+getItemId(position).toString())
       /*imageDeleteTrash.setOnClickListener {

        }*/
        return itemView
    }

    fun getTitle(position: Int): Any{
        return activities[position].title.toString()
    }

    fun getContent(position: Int): Any{
        return activities[position].content.toString()
    }

    fun getHour(position: Int): Any{
        return activities[position].hour.toString()
    }

    fun getDate(position: Int): Any{
        return activities[position].date.toString()
    }

}