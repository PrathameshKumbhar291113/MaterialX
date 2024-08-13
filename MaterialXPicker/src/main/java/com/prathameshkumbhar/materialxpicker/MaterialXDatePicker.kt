package com.prathameshkumbhar.materialxpicker

import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.datepicker.MaterialDatePicker

class MaterialXDatePicker{



    fun showMaterialXDatePicker(
        lifecycleOwner: LifecycleOwner,
        isCancelable: Boolean,
        onDateSelected: (Long) -> Unit,
        onError: (String) -> Unit = {},
        customizations: (MaterialDatePicker.Builder<Long>) -> Unit = {}
    ) {
        try {
            val fragmentManager = when (lifecycleOwner) {
                is FragmentActivity -> lifecycleOwner.supportFragmentManager
                is Fragment -> lifecycleOwner.childFragmentManager
                else -> throw IllegalArgumentException("Invalid LifecycleOwner")
            }

            val builder = MaterialDatePicker.Builder.datePicker()
            customizations(builder)
            val picker = builder.build()
            picker.isCancelable = isCancelable

            picker.addOnPositiveButtonClickListener { selection ->
                if (selection == null) {
                    onError("Date selection is null.")
                } else {
                    onDateSelected(selection)
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

    fun showMaterialXDateRangePicker(
        lifecycleOwner: LifecycleOwner,
        isCancelable: Boolean,
        onDateRangeSelected: (Pair<Long, Long>) -> Unit,
        onError: (String) -> Unit = {},
        customizations: (MaterialDatePicker.Builder<Pair<Long, Long>>) -> Unit = {}
    ) {
        try {
            val fragmentManager = when (lifecycleOwner) {
                is FragmentActivity -> lifecycleOwner.supportFragmentManager
                is Fragment -> lifecycleOwner.childFragmentManager
                else -> throw IllegalArgumentException("Invalid LifecycleOwner")
            }

            val builder = MaterialDatePicker.Builder.dateRangePicker()
            customizations(builder)
            val picker = builder.build()
            picker.isCancelable = isCancelable

            picker.addOnPositiveButtonClickListener { selection ->
                if (selection?.first == null || selection.second == null) {
                    onError("Date range selection is null.")
                } else if (selection.first > selection.second) {
                    onError("Invalid date range: start date is after end date.")
                } else {
                    onDateRangeSelected(Pair(selection.first, selection.second))
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