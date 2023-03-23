package com.example.agecalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    //creating a var for date and age in min so that we can access it in date picker fun
    private var selectedDate : TextView? = null
    private var ageInMin : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //selecting button and text box we want to put the date and age in min
        val dateBtn : Button = findViewById(R.id.dateBtn)

        selectedDate = findViewById(R.id.selectedDate)

        ageInMin = findViewById(R.id.minutes)

        //when button is clicked datePicker fun runs which opens the date picker
        dateBtn.setOnClickListener {
            datePicker()
        }

    }

    private fun datePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _ , selectedYear, selectedMonth, selectedDayOfMonth ->
                val userDate = "${selectedMonth+1}/$selectedDayOfMonth/$selectedYear"
                selectedDate?.text = userDate

                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(userDate)
                //since theDate can be null we need to use .let to be safe
                theDate?.let{
                    val selectedDateInMin = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    //since currentDate can be null we need to use .let to be safe
                    currentDate?.let {
                        val currentDateInMin = currentDate.time / 60000

                        val timePassedInMin = currentDateInMin - selectedDateInMin

                        ageInMin?.text = timePassedInMin.toString()
                    }
                }

            },
            year,
            month,
            day
        )
        //making it so the user can't pick a date in the future
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}