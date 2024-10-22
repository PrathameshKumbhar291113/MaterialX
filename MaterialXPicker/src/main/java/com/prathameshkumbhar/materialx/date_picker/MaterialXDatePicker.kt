package com.prathameshkumbhar.materialx.date_picker

/*
 * Copyright 2024 Prathamesh Kumbhar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker

class MaterialXDatePicker{

    fun showMaterialXDatePicker(
        lifecycleOwner: LifecycleOwner,
        isCancelable: Boolean,
        onDateSelected: (Long) -> Unit,
        onError: (String) -> Unit = {},
        restrictFutureDates: Boolean = false,
        customizations: (MaterialDatePicker.Builder<Long>) -> Unit = {}
    ) {
        try {
            val fragmentManager = when (lifecycleOwner) {
                is FragmentActivity -> lifecycleOwner.supportFragmentManager
                is Fragment -> lifecycleOwner.childFragmentManager
                else -> throw IllegalArgumentException("Invalid LifecycleOwner")
            }

            val builder = MaterialDatePicker.Builder.datePicker()

            if (restrictFutureDates) {
                builder.setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setEnd(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()
                )
            }

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

}