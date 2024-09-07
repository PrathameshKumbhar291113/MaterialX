package com.prathameshkumbhar.materialx

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
fun MaterialXJPCTimePicker(
    lifecycleOwner: LifecycleOwner,
    isCancelable: Boolean,
    is24HourFormat: Boolean = false,
    onTimeSelected: (Int, Int) -> Unit,
    onError: (String) -> Unit = {},
    customizations: (MaterialTimePicker.Builder) -> Unit = {}
) {

    val fragmentManager = when (lifecycleOwner) {
        is FragmentActivity -> lifecycleOwner.supportFragmentManager
        is Fragment -> lifecycleOwner.childFragmentManager
        else -> throw IllegalArgumentException("Invalid LifecycleOwner")
    }

    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        val builder = MaterialTimePicker.Builder()
            .setTimeFormat(if (is24HourFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)

        customizations(builder)

        val picker = builder.build()

        picker.isCancelable = isCancelable

        Dialog(onDismissRequest = { showDialog = false }) {
            AndroidView(factory = { context ->
                FrameLayout(context).apply {
                    picker.show(fragmentManager, picker.toString())
                }
            })

            picker.addOnPositiveButtonClickListener {
                val hour = picker.hour
                val minute = picker.minute

                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    onError("Invalid time selected.")
                } else {
                    onTimeSelected(hour, minute)
                }
                showDialog = false
            }

            picker.addOnNegativeButtonClickListener {
                picker.dismiss()
                showDialog = false
            }
        }
    }
}
