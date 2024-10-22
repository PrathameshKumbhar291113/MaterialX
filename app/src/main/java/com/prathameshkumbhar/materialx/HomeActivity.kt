package com.prathameshkumbhar.materialx

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.prathameshkumbhar.materialx.custom_dialog.MaterialXCustomDialogs
import com.prathameshkumbhar.materialx.databinding.ActivityHomeBinding
import com.prathameshkumbhar.materialx.date_picker.MaterialXDatePicker
import com.prathameshkumbhar.materialx.date_range_picker.MaterialXDateRangePicker
import com.prathameshkumbhar.materialx.time_picker.MaterialXTimePicker

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@HomeActivity, R.layout.activity_home)

        window.statusBarColor = resources.getColor(R.color.heliotrope1)

        //Material X Date Picker
        materialXDatePicker()

        //Material X Date Range Picker
        materialXDateRangePicker()

        //Material X Time Picker
        materialXTimePicker()

        //Material X Custom Dialog Box
        materialXCustomDialogBox()

        //Goto compose activity
        gotoComposeActivity()

    }

    private fun gotoComposeActivity() {
       binding.cardComposeBox.setOnClickListener {
           val intent = Intent(this, JPCActivity::class.java)
           startActivity(intent)
       }
    }

    private fun materialXDatePicker() {

        binding.cardDatePicker.setOnClickListener {

            MaterialXDatePicker().showMaterialXDatePicker(
                lifecycleOwner = this,
                isCancelable = true,
                onDateSelected = { selectedDate ->
                    // Handle the selected date
                    Log.d("DatePicker", "Selected date: $selectedDate")
                    Toast.makeText(this,
                        "Date Selected: $selectedDate",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = { error ->
                    Toast.makeText(this,
                        "Error Occurred: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                restrictFutureDates = false,
                restrictPastDates = true,
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialCalendar)
                }
            )
        }

    }


    private fun materialXDateRangePicker() {

        binding.cardDateRangePicker.setOnClickListener {

            MaterialXDateRangePicker().showMaterialXDateRangePicker(
                lifecycleOwner = this,
                isCancelable = true,
                onDateRangeSelected = { selectedDateRange ->
                    // Handle the selected date range
                    Log.d("DatePicker",
                        "Selected first date: ${selectedDateRange.first} --- second date: ${selectedDateRange.second}"
                    )
                    Toast.makeText(this,
                        "Selected first date: ${selectedDateRange.first} --- second date: ${selectedDateRange.second}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = { error ->
                    Toast.makeText(this,
                        "Error Occurred: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialCalendar)
                }
            )
        }

    }

    private fun materialXTimePicker() {

        binding.cardTimePicker.setOnClickListener {

            MaterialXTimePicker().showMaterialXTimePicker(
                lifecycleOwner = this,
                isCancelable = true,
                is24HourFormat = false,
                onTimeSelected = { hour, minute->
                    // Handle the selected time
                    Log.d("DatePicker", "Selected Time  H: $hour  M: $minute")
                    Toast.makeText(this,
                        "Selected Time  H: $hour  M: $minute",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = { error ->
                    Toast.makeText(this,
                        "Error Occurred: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialTimePicker)
                }
            )
        }

    }

    private fun materialXCustomDialogBox() {

        binding.cardAlertDialogBox.setOnClickListener {

            val customDialog = MaterialXCustomDialogs.MaterialXCustomDialogBuilder(context = this)
                .setTitle("Welcome")
                .setDescription("Thank you for choosing MaterialX. Enjoy your experience!")
                .setPositiveButton("OK") {
                    // Positive button action
                }
                .setCancelable(true)
                .build()

            customDialog.show()
        }

    }
}