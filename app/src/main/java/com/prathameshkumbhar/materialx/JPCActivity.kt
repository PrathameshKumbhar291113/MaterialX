package com.prathameshkumbhar.materialx

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.prathameshkumbhar.materialx.databinding.ActivityJpcBinding
import com.prathameshkumbhar.materialx.ui.theme.MaterialXJPCTheme


@SuppressLint("RestrictedApi")
class JPCActivity() : ComponentActivity() {
    private lateinit var binding: ActivityJpcBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@JPCActivity, R.layout.activity_jpc)

        binding.composeView.setContent {
            MaterialXJPCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {

                }
            }
        }
    }
}


@Composable
fun MaterialXJPCDatePickerExample() {
    val lifeCycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current

    MaterialXJPCDatePicker(
        lifecycleOwner = lifeCycleOwner,
        isCancelable = true,
        onDateSelected = { selectedDate ->
            // Handle the selected date
            Log.d("DatePicker", "Selected date: $selectedDate")
            Toast.makeText(
                context,
                "Date Selected: $selectedDate",
                Toast.LENGTH_SHORT
            ).show()
        },
        onError = { error ->
            Toast.makeText(context,
                "Error Occurred: $error",
                Toast.LENGTH_SHORT
            ).show()
        },
        customizations = { customization ->
            customization.setTheme(R.style.ThemeMaterialCalendar)
        }
    )

}