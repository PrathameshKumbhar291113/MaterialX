package com.prathameshkumbhar.materialxpicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.datepicker.MaterialDatePicker

class MaterialXDatePicker{


    fun showMaterialXDatePicker(
        lifecycleOwner: LifecycleOwner,
        isCancelable: Boolean,
        onDateSelected: (Long) -> Unit,
        customizations: (MaterialDatePicker.Builder<Long>) -> Unit = {}
    ) {

        val fragmentManager = when (lifecycleOwner) {
            is FragmentActivity -> lifecycleOwner.supportFragmentManager
            is Fragment -> lifecycleOwner.childFragmentManager
            else -> throw IllegalArgumentException("Invalid LifecycleOwner")
        }

        val builder = MaterialDatePicker.Builder.datePicker()
        customizations(builder)
        val picker = builder.build()
        picker.isCancelable = isCancelable
        picker.addOnPositiveButtonClickListener { onDateSelected(it) }
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.show(fragmentManager, picker.toString())
    }

}