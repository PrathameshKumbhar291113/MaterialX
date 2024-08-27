package com.prathameshkumbhar.materialx

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prathameshkumbhar.materialx.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.heliotrope1)

        //Material X Date Picker
        materialXDatePicker()

        //Material X Date Range Picker
        materialXDateRangePicker()

        //Material X Time Picker
        materialXTimePicker()

    }

    private fun materialXDatePicker() {

        binding.cardDatePicker.setOnClickListener {

            MaterialXDatePicker().showMaterialXDatePicker(
                lifecycleOwner = this,
                isCancelable = true,
                onDateSelected = { selectedDate ->
                    // Handle the selected date
                    Log.d("DatePicker", "Selected date: $selectedDate")
                    Toast.makeText(this, "Date Selected: $selectedDate", Toast.LENGTH_SHORT).show()
                },
                onError = { error ->
                    Toast.makeText(this, "Error Occurred: $error", Toast.LENGTH_SHORT).show()
                },
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
                    // Handle the selected date
                    Log.d("DatePicker", "Selected first date: ${selectedDateRange.first} --- second date: ${selectedDateRange.second}")
                    Toast.makeText(this, "Selected first date: ${selectedDateRange.first} --- second date: ${selectedDateRange.second}", Toast.LENGTH_SHORT).show()
                },
                onError = { error ->
                    Toast.makeText(this, "Error Occurred: $error", Toast.LENGTH_SHORT).show()
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
                onTimeSelected = { hour, minute->
                    // Handle the selected date
                    Log.d("DatePicker", "Selected Time  H: $hour  M: $minute")
                    Toast.makeText(this, "Selected Time  H: $hour  M: $minute", Toast.LENGTH_SHORT).show()
                },
                onError = { error ->
                    Toast.makeText(this, "Error Occurred: $error", Toast.LENGTH_SHORT).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialTimePicker)
                }
            )
        }

    }

    private fun materialXCustomDialogBox() {

        binding.cardDatePicker.setOnClickListener {

            MaterialXDatePicker().showMaterialXDatePicker(
                lifecycleOwner = this,
                isCancelable = true,
                onDateSelected = { selectedDate ->
                    // Handle the selected date
                    Log.d("DatePicker", "Selected date: $selectedDate")
                    Toast.makeText(this, "Date Selected: $selectedDate", Toast.LENGTH_SHORT).show()
                },
                onError = { error ->
                    Toast.makeText(this, "Error Occurred: $error", Toast.LENGTH_SHORT).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialCalendar)
                }
            )
        }

    }
}