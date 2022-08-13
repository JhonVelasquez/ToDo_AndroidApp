package com.example.todo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.add_activity_data.*
import kotlinx.android.synthetic.main.date_picker.*
import kotlinx.android.synthetic.main.edit_activity_data.*
import kotlinx.android.synthetic.main.edit_activity_data.input_text_contenido_edit
import kotlinx.android.synthetic.main.edit_activity_data.input_text_hora_edit
import kotlinx.android.synthetic.main.edit_activity_data.input_text_titulo_edit
import java.sql.Timestamp
import java.text.SimpleDateFormat

class AddActivity: AppCompatActivity() {
    private val fecha_completa = SimpleDateFormat("dd-MM-yyyy")
    private val hora_completa= SimpleDateFormat("HH:mm")
    private val day = SimpleDateFormat("dd")
    private val month=SimpleDateFormat("MM")
    private val year=SimpleDateFormat("yyyy")
    private val hour=SimpleDateFormat("HH")
    private val minute=SimpleDateFormat("mm")
    var timeStamp = Timestamp(System.currentTimeMillis())
    var fecha_completa_form=fecha_completa.format(timeStamp).toString()
    var hora_completa_form=hora_completa.format(timeStamp).toString()
    var year_form=year.format(timeStamp).toString()
    var month_form=month.format(timeStamp).toString()
    var day_form=day.format(timeStamp).toString()
    var hour_form=hour.format(timeStamp).toString()
    var minute_form=minute.format(timeStamp).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity_data)

        if(intent.getStringExtra("dateFromMain")!=null){
            fecha_completa_form=(intent.getStringExtra("dateFromMain").toString())
        }
        if(intent.getStringExtra("dayFromMain")!=null){
            day_form=(intent.getStringExtra("dayFromMain").toString())
        }
        if(intent.getStringExtra("monthFromMain")!=null){
            month_form=(intent.getStringExtra("monthFromMain").toString())
        }
        if(intent.getStringExtra("yearFromMain")!=null){
            year_form=(intent.getStringExtra("yearFromMain").toString())
        }


        input_text_fecha_add.setText(fecha_completa_form)
        input_text_hora_add.setText(hora_completa_form)



        btn_add.setOnClickListener {
            //listActivities[position].hour=dialog.input_text_hora.text.toString()
            //listActivities[position].title=dialog.input_text_titulo.text.toString()
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "todoDB"
            ).allowMainThreadQueries().enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration().build()
            val activityDao = db.ActivityDao()

            activityDao.insertAll(Activity(input_text_titulo_add.text.toString(),input_text_contenido_add.text.toString(),input_text_hora_add.text.toString(),input_text_fecha_add.text.toString()))

            //activityDao.updateActivity(actVar)
            var date_from_main=""
            if(intent.getStringExtra("dateFromMain")!=null){
                date_from_main=(intent.getStringExtra("dateFromMain").toString())
            }else{
                date_from_main=""
            }
            Log.i( "date_from_main_add", date_from_main)

            var intent= Intent(this, MainActivity::class.java)
            intent.putExtra("dateStartUpFromEdit",date_from_main)
            startActivity(intent)
        }
        button_arrow_fecha_hora_add.setOnClickListener {
            val dialog= Dialog(this)
            dialog.setTitle("Date and hour selector")
            dialog.setContentView(R.layout.date_picker)


            dialog.time_picker_item.setIs24HourView(true)

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
                input_text_fecha_add.setText(day_form.toString()+"-"+month_form.toString()+"-"+year_form.toString())
                var hourText=String.format("%02d",dialog.time_picker_item.hour.toInt())
                var minText=String.format("%02d",dialog.time_picker_item.minute.toInt())
                input_text_hora_add.setText(hourText+":"+minText)


                dialog.dismiss()
            }
            dialog.button_cancelar.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

    }

}