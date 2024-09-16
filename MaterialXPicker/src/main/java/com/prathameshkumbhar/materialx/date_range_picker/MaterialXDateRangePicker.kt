package com.prathameshkumbhar.materialx.date_range_picker

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

import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.datepicker.MaterialDatePicker

class MaterialXDateRangePicker {

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