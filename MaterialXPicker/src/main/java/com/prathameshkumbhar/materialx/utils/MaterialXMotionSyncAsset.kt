package com.prathameshkumbhar.materialx.utils

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

import android.graphics.PointF
import androidx.compose.runtime.Composable

data class MaterialXMotionSyncAsset(
    val id: String,
    val title: String,
    val iconResId: Int? = null,
    val customContent: (@Composable () -> Unit)? = null,
    val contentType: MaterialXMotionSyncContentType = MaterialXMotionSyncContentType.IMAGE,
    val initialPosition: PointF = PointF(0f, 0f),
    val size: Float = 100f

)
