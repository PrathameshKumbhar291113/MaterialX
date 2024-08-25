package com.prathameshkumbhar.materialx

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