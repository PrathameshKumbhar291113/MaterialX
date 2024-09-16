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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.datepicker.MaterialDatePicker
import kotlin.Boolean
import kotlin.IllegalArgumentException
import kotlin.Long
import kotlin.Pair
import kotlin.String
import kotlin.Unit
import androidx.core.util.Pair as AndroidXPair


@Composable
fun MaterialXJPCDateRangePicker(
    lifecycleOwner: LifecycleOwner,
    isCancelable: Boolean,
    onDateRangeSelected: (Pair<Long, Long>) -> Unit,
    onError: (String) -> Unit = {},
    customizations: (MaterialDatePicker.Builder<AndroidXPair<Long, Long>>) -> Unit = {}
) {

    val fragmentManager = when (lifecycleOwner) {
        is FragmentActivity -> lifecycleOwner.supportFragmentManager
        is Fragment -> lifecycleOwner.childFragmentManager
        else -> throw IllegalArgumentException("Invalid LifecycleOwner")
    }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showDialog = true
    }

    if (showDialog) {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        customizations(builder)
        val picker = builder.build()

        picker.isCancelable = isCancelable

        Dialog(onDismissRequest = { showDialog = false }) {

            picker.addOnPositiveButtonClickListener { selection ->
                if (selection?.first == null || selection.second == null) {
                    onError("Date range selection is null.")
                } else if (selection.first > selection.second) {
                    onError("Invalid date range: start date is after end date.")
                } else {
                    val kotlinPair = Pair(selection.first, selection.second)
                    onDateRangeSelected(kotlinPair)
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
