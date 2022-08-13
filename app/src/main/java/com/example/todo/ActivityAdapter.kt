package com.example.todo

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.list_activity_item.view.*

class ActivityAdapter(): BaseAdapter() {
    lateinit var context: Context
    lateinit var activityNames: ArrayList<String>
    lateinit var activityHours: ArrayList<String>
    lateinit var activityID: ArrayList<Long>
    constructor(parcel: Parcel): this(){

    }

    constructor(context: Context,activityNames: ArrayList<String>,activityID: ArrayList<Long>,activityHours: ArrayList<String>):this(){
        this.context=context
        this.activityNames=activityNames
        this.activityID=activityID
        this.activityHours=activityHours
    }

    override fun getCount(): Int {
        return activityNames.size
    }

    override fun getItem(position: Int): Any {
        return activityNames[position]
    }

    override fun getItemId(position: Int): Long {
        //return position.toLong()
        return activityID[position]
    }

    fun getHour(position: Int): Any{
        return activityHours[position]
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = layoutInflater.inflate(R.layout.list_activity_item, null, true)
        val activityTextView=itemView.activity_item_text as TextView
        val activityTextID=itemView.activity_item_id as TextView
        val activityHour=itemView.activity_item_hour as TextView
        activityTextView.text=getItem(position).toString()
        activityTextID.text=getItemId(position).toString()
        activityHour.text=getHour(position).toString()
        return itemView
    }



    companion object CREATOR : Parcelable.Creator<ActivityAdapter> {
        override fun createFromParcel(parcel: Parcel): ActivityAdapter {
            return ActivityAdapter(parcel)
        }

        override fun newArray(size: Int): Array<ActivityAdapter?> {
            return arrayOfNulls(size)
        }
    }

}