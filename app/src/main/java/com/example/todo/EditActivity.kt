package com.example.todo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.BaseAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_activity_data.*
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.edit_activity_data.*
import java.sql.Timestamp
import java.text.SimpleDateFormat

class EditActivity: AppCompatActivity() {
    private val fecha_completa = SimpleDateFormat("dd-MM-yyyy")
    private val hora_completa= SimpleDateFormat("HH:mm")
    private val day = SimpleDateFormat("dd")
    private val month=SimpleDateFormat("MM")
    private val year=SimpleDateFormat("yyyy")
    private val hour=SimpleDateFormat("HH")
    private val minute=SimpleDateFormat("mm")

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity_data)
        var idReceived=intent.getStringExtra("idPassed").toString()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todoDB"
        ).allowMainThreadQueries().enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration().build()
        val activityDao = db.ActivityDao()

        var activity=activityDao.loadById(idReceived.toInt())

        input_text_hora_edit.setText(activity.hour)
        input_text_titulo_edit.setText(activity.title)
        input_text_contenido_edit.setText(activity.content)
        input_text_fecha_edit.setText(activity.date)

        btn_update.setOnClickListener {
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

            activityDao.updateTitle(activity.activityID,input_text_titulo_edit.text.toString())
            activityDao.updateHour(activity.activityID, input_text_hora_edit.text.toString())
            activityDao.updateContent(activity.activityID,input_text_contenido_edit.text.toString())
            activityDao.updateDate(activity.activityID,input_text_fecha_edit.text.toString())
            //activityDao.updateActivity(actVar)
            var intent= Intent(this, MainActivity::class.java)

            intent.putExtra("dateStartUpFromEdit",input_text_fecha_edit.text.toString())
            startActivity(intent)
        }

        btn_delete.setOnClickListener{
            //listActivities[position].hour=dialog.input_text_hora.text.toString()
            //listActivities[position].title=dialog.input_text_titulo.text.toString()
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "todoDB"
            ).allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
            val activityDao = db.ActivityDao()

            activityDao.delete(activityDao.loadById(activity.activityID))

            var intent= Intent(this, MainActivity::class.java)
            var date_from_main=""
            if(intent.getStringExtra("dateFromMain")!=null){
                date_from_main=(intent.getStringExtra("dateFromMain").toString())
            }else{
                date_from_main=""
            }
            intent.putExtra("dateStartUpFromEdit",date_from_main)

            startActivity(intent)
        }


        button_arrow_fecha_hora.setOnClickListener {
            val dialog= Dialog(this)
            dialog.setTitle("Date and hour selector")
            dialog.setContentView(R.layout.date_picker)


            dialog.time_picker_item.setIs24HourView(true)


            var timeStamp = Timestamp(System.currentTimeMillis())
            var fecha_completa_form=fecha_completa.format(timeStamp).toString()
            var hora_completa_form=hora_completa.format(timeStamp).toString()
            var year_form=year.format(timeStamp).toString()
            var month_form=month.format(timeStamp).toString()
            var day_form=day.format(timeStamp).toString()
            var hour_form=hour.format(timeStamp).toString()
            var minute_form=minute.format(timeStamp).toString()


            dialog.date_picker_item.init(year_form.toInt(),month_form.toInt()-1,day_form.toInt(),
                DatePicker.OnDateChangedListener{ view, year, monthOfYear, dayOfMonth ->
                    //day_form=dayOfMonth.toString()
                    year_form=year.toString()
                    //month_form=(monthOfYear+1).toString()
                    day_form=String.format("%02d", dayOfMonth)
                    //year_form=String.format("%02d", year)
                    month_form=String.format("%02d", monthOfYear+1)
                })


            dialog.button_aceptar.setOnClickListener {
                input_text_fecha_edit.setText(day_form.toString()+"-"+month_form.toString()+"-"+year_form.toString())
                var hourText=String.format("%02d",dialog.time_picker_item.hour.toInt())
                var minText=String.format("%02d",dialog.time_picker_item.minute.toInt())
                input_text_hora_edit.setText(hourText+":"+minText)
                //input_text_hora_edit.setText(dialog.time_picker_item.hour.toString()+":"+dialog.time_picker_item.minute.toString())
                dialog.dismiss()
            }
            dialog.button_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


    }

    fun anotherFunction(){

    }

}