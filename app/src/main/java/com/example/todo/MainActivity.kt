package com.example.todo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_activity_data.*
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.edit_activity_data.*
import kotlinx.android.synthetic.main.list_activity_item.*
import java.util.*
import kotlin.collections.ArrayList
import java.sql.Timestamp
import java.text.SimpleDateFormat
import kotlinx.android.synthetic.main.add_activity_data.button_arrow_fecha_hora_add as button_arrow_fecha_hora_add1

class MainActivity : AppCompatActivity() {
    private val fecha_completa = SimpleDateFormat("dd-MM-yyyy")
    private val hora_completa= SimpleDateFormat("HH:mm")
    private val day = SimpleDateFormat("dd")
    private val month=SimpleDateFormat("MM")
    private val year=SimpleDateFormat("yyyy")
    private val hour=SimpleDateFormat("HH")
    private val minute=SimpleDateFormat("mm")
    var fecha_form="00-00-00"
    var hora_form="00:00"
    var year_form="0000"
    var month_form="00"
    var day_form="00"
    var hour_form="00"
    var minute_form="00"
    /*
    private val listActivitys = ArrayList<String>()
    private val listHour = ArrayList<String>()
    private val listID = ArrayList<Long>()
    */
    private val listActivities = ArrayList<Activity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timeStamp = Timestamp(System.currentTimeMillis())

        if(intent.getStringExtra("dateStartUpFromEdit")!=null){
            fecha_form=(intent.getStringExtra("dateStartUpFromEdit").toString())
        }else{
            fecha_form=fecha_completa.format(timeStamp).toString()
        }

        //fecha_form=fecha_completa.format(timeStamp).toString()
        Log.i( "fecha_form_main", fecha_form)


        hora_form=hora_completa.format(timeStamp).toString()
        year_form=year.format(timeStamp).toString()
        month_form=month.format(timeStamp).toString()
        day_form=day.format(timeStamp).toString()
        hour_form=hour.format(timeStamp).toString()
        minute_form=minute.format(timeStamp).toString()

        text_input_fecha.setText(fecha_form)
        text_input_hora.setText(hora_form)
        text_input_hora.visibility=View.GONE
        text_hora.visibility=View.GONE

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todoDB"
        ).allowMainThreadQueries().enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration().build()
        val activityDao = db.ActivityDao()
        //activityDao.insertAll(Activity("sadsa","asda","asda","sada"))
        listActivities.clear()
        listActivities.addAll(activityDao.getBasedOnDate(fecha_form))
        var activityAdapter = ActivityAdapterThird(this, listActivities)

        list_activities.adapter = activityAdapter
        list_activities.setOnItemClickListener{
                parent, view, position, id ->eventItemClickedSendEditView(position,listActivities[position],activityAdapter)
        }

        /*
        for (Activity in listActivities) {
            var tag = "button_" + Activity.activityID
            Log.i("MyAppTag", tag)
            val img =
                findViewById<View>(R.id.list_activities).findViewWithTag<View>(tag) as ImageView
            img.setOnClickListener {
                Log.i("Cree el", tag)
            }
        }
        */
            //list_activities.setOnItemClickListener { parent, view, position, id -> eventItemClicked(position,listID[position].toString(),listHour[position],listActivitys[position],activityAdapter) }

        button_add_main.setOnClickListener {
            var intent=Intent(this, AddActivity::class.java)
            intent.putExtra("dateFromMain",day_form+"-"+month_form+"-"+year_form)
            intent.putExtra("dayFromMain",day_form)
            intent.putExtra("monthFromMain",month_form)
            intent.putExtra("yearFromMain",year_form)
            startActivity(intent)
        }

        button_arrow_fecha_hora_add.setOnClickListener {
            val dialog= Dialog(this)
            var changeListExec=0
            dialog.setTitle("Date and hour selector")
            dialog.setContentView(R.layout.date_picker)
            dialog.time_picker_item.setIs24HourView(true)
            dialog.time_picker_item.visibility=View.GONE
            dialog.date_picker_item.init(year_form.toInt(),month_form.toInt()-1,day_form.toInt(),
                DatePicker.OnDateChangedListener{view, year, monthOfYear, dayOfMonth ->
                    //day_form=dayOfMonth.toString()
                    //year_form=year.toString()
                    //month_form=(monthOfYear+1).toString()
                    day_form=String.format("%02d", dayOfMonth)
                    //year_form=String.format("%02d", year)
                    month_form=String.format("%02d", monthOfYear+1)
                    changeListExec=1
                })


            dialog.button_aceptar.setOnClickListener {
                text_input_fecha.setText(day_form+"-"+month_form+"-"+year_form)
                text_input_hora.setText(dialog.time_picker_item.hour.toString()+":"+dialog.time_picker_item.minute.toString())
                listActivities.clear()
                //listActivities.addAll(activityDao.getBasedOnDate(text_input_fecha.text.toString()))

                listActivities.addAll(activityDao.getBasedOnDateOrdered(text_input_fecha.text.toString()))
                var activityAdapter = ActivityAdapterThird(this, listActivities)

                list_activities.adapter = activityAdapter
                list_activities.setOnItemClickListener{
                        parent, view, position, id ->eventItemClickedSendEditView(position,listActivities[position],activityAdapter)
                }
                dialog.dismiss()

            }
            dialog.button_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        }
        /*
    private fun eventItemClicked(position: Int,id: String, hora: String, titulo:String,  activityAdapter: ActivityAdapter) {
        val dialog= Dialog(this)
        dialog.setTitle("Modify flower name")
        dialog.setContentView(R.layout.edit_activity_data)
        dialog.input_text_hora.setText(hora)
        dialog.input_text_titulo.setText(titulo)
        dialog.input_text_contenido.setText(id)

        dialog.btn_update.setOnClickListener {
            //listHour[position]=dialog.input_text_hora.text.toString()
            //listActivitys[position]=dialog.input_text_titulo.text.toString()
            (activityAdapter as BaseAdapter).notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()

    }
    */

    fun eventItemClickedSendEditView(position: Int,activity: Activity,activityAdapter: ActivityAdapterThird) {
        var intent=Intent(this, EditActivity::class.java)
        intent.putExtra("idPassed",activity.activityID.toString())
        intent.putExtra("dateFromMain",fecha_form)
        startActivity(intent)
    }

    fun eventItemClickedSecond(
            position: Int,
            activity: Activity,
            activityAdapter: ActivityAdapterThird
        ){

            val dialog = Dialog(this)
            dialog.setTitle("Modify flower name")
            dialog.setContentView(R.layout.edit_activity_data)
            dialog.input_text_hora_add.setText(activity.hour)
            dialog.input_text_titulo_add.setText(activity.title)
            dialog.input_text_contenido_add.setText(activity.content)

            dialog.btn_update.setOnClickListener {
                //listActivities[position].hour=dialog.input_text_hora.text.toString()
                //listActivities[position].title=dialog.input_text_titulo.text.toString()
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "todoDB"
                ).allowMainThreadQueries().enableMultiInstanceInvalidation()
                    .fallbackToDestructiveMigration().build()
                val activityDao = db.ActivityDao()
                //var actVar=Activity(dialog.input_text_titulo.text.toString(),activity.content,dialog.input_text_hora.text.toString(),activity.date.toString())
                //var actVar=activityDao.loadById(dialog.activity_item_id.text.toString().toInt())

                activityDao.updateTitle(
                    activity.activityID,
                    dialog.input_text_titulo_add.text.toString()
                )
                activityDao.updateHour(activity.activityID, dialog.input_text_hora_add.text.toString())

                //activityDao.updateActivity(actVar)
                (activityAdapter as BaseAdapter).notifyDataSetChanged()
                dialog.dismiss()
                actualizarListWithDatabase()
            }
            dialog.show()

        }


    private fun actualizarListWithDatabase() {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "todoDB"
            ).allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
            val activityDao = db.ActivityDao()
            //val act = Activity("tituloPrueba12","contentPrueba2","hora6","fech3")
            //activityDao.insertAll(act)


            //listID.clear()
            //activitys: List<Activity> = ActivityDao.getAll()
            listActivities.clear()
            listActivities.addAll(activityDao.getAll())
            /*
        for (Activity in listActivities){
            listActivitys.add(Activity.title.toString())
            listID.add(Activity.activityID.toLong())
            listHour.add(Activity.hour.toString())

        }*/
            //var activityAdapter= ActivityAdapter(this,listActivitys,listID,listHour)
            var activityAdapter = ActivityAdapterThird(this, listActivities)
            list_activities.adapter = activityAdapter
        }



}