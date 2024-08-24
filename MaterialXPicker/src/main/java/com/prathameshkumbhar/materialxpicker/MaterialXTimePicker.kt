package com.prathameshkumbhar.materialxpicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.timepicker.MaterialTimePicker

class MaterialXTimePicker {

    fun showMaterialXTimePicker(
        lifecycleOwner: LifecycleOwner,
        isCancelable: Boolean,
        onTimeSelected: (Int, Int) -> Unit,
        onError: (String) -> Unit = {},
        customizations: (MaterialTimePicker.Builder) -> Unit = {}
    ) {
        try {

            val fragmentManager = when (lifecycleOwner) {
                is FragmentActivity -> lifecycleOwner.supportFragmentManager
                is Fragment -> lifecycleOwner.childFragmentManager
                else -> throw IllegalArgumentException("Invalid LifecycleOwner")
            }

            val builder = MaterialTimePicker.Builder()
            customizations(builder)
            val picker = builder.build()
            picker.isCancelable = isCancelable

            picker.addOnPositiveButtonClickListener {
                val hour = picker.hour
                val minute = picker.minute

                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    onError("Invalid time selected.")
                } else {
                    onTimeSelected(hour, minute)
                }
            }

            picker.addOnNegativeButtonClickListener { picker.dismiss() }

            picker.show(fragmentManager, picker.toString())

        } catch (e: IllegalArgumentException) {
            onError("Error: ${e.message}")
        } catch (e: Exception) {
            onError("An unexpected error occurred: ${e.localizedMessage}")
        }
    }

}