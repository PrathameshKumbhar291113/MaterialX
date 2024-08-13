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
        customizations: (MaterialTimePicker.Builder) -> Unit = {}
    ) {

        val fragmentManager = when (lifecycleOwner) {
            is FragmentActivity -> lifecycleOwner.supportFragmentManager
            is Fragment -> lifecycleOwner.childFragmentManager
            else -> throw IllegalArgumentException("Invalid LifecycleOwner")
        }

        val builder = MaterialTimePicker.Builder()
        customizations(builder)
        val picker = builder.build()
        picker.isCancelable = isCancelable
        picker.addOnPositiveButtonClickListener { onTimeSelected(picker.hour, picker.minute) }
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.show(fragmentManager, picker.toString())
    }
}