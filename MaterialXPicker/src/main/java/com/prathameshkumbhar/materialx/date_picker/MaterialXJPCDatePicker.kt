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


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker

@Composable
fun MaterialXJPCDatePicker(
    lifecycleOwner: LifecycleOwner,
    isCancelable: Boolean = true,
    onDateSelected: (Long) -> Unit,
    onError: (String) -> Unit = {},
    restrictFutureDates: Boolean = false,
    customizations: (MaterialDatePicker.Builder<Long>) -> Unit = {}
) {

    val fragmentManager = when (lifecycleOwner) {
        is FragmentActivity -> lifecycleOwner.supportFragmentManager
        else -> throw IllegalArgumentException("Invalid LifecycleOwner")
    }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showDialog = true
    }

    if (showDialog) {
        val builder = MaterialDatePicker.Builder.datePicker()

        if (restrictFutureDates) {
            val constraintsBuilder = CalendarConstraints.Builder()
            constraintsBuilder.setValidator(DateValidatorPointBackward.now())

            builder.setCalendarConstraints(constraintsBuilder.build())
        }

        customizations(builder)
        val picker = builder.build()

        Dialog(onDismissRequest = { showDialog = false }) {
            picker.isCancelable = isCancelable

            picker.addOnPositiveButtonClickListener { selection ->
                if (selection == null) {
                    onError("Date selection is null.")
                } else {
                    onDateSelected(selection)
                }
                showDialog = false
            }

            picker.addOnNegativeButtonClickListener {
                picker.dismiss()
                showDialog = false
            }

            picker.show(fragmentManager, picker.toString())
        }
    }
}
