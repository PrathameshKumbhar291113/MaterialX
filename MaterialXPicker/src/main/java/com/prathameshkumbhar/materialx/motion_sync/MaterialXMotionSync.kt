package com.prathameshkumbhar.materialx.motion_sync

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prathameshkumbhar.materialx.motion_sync.utils.MotionSyncAlignment
import com.prathameshkumbhar.materialx.motion_sync.utils.MotionSyncLayout
import com.prathameshkumbhar.materialx.motion_sync.utils.MotionSyncSensorHandler
import com.prathameshkumbhar.materialx.motion_sync.utils.motionSyncResolveCollisionsAndAdjustVelocities
import com.prathameshkumbhar.materialx.motion_sync.utils.motionSyncSetupInitialComponentPositions
import com.prathameshkumbhar.materialx.motion_sync.utils.toPx

@Composable
fun MaterialXMotionSync(
    composableList: List<@Composable () -> Unit>,
    size: Dp,
    layoutDirection: MotionSyncLayout,
    alignmentOption: MotionSyncAlignment,
    spacing: Dp,
    speedMultiplier: Float
) {
    val context = LocalContext.current
    val sensorHandler = remember { MotionSyncSensorHandler(context) }
    val density = LocalDensity.current.density
    val displayMetrics = context.resources.displayMetrics
    val screenWidthPx = displayMetrics.widthPixels.toFloat()
    val screenHeightPx = displayMetrics.heightPixels.toFloat()

    val initialPositions = remember {
        motionSyncSetupInitialComponentPositions(
            composableList.size,
            layoutDirection,
            alignmentOption,
            size,
            spacing,
            screenWidthPx,
            screenHeightPx,
            density
        )
    }

    var positions by remember { mutableStateOf(initialPositions) }
    var velocities by remember { mutableStateOf(List(composableList.size) { PointF(0f, 0f) }) }


    DisposableEffect(Unit) {
        sensorHandler.onSensorChanged = { x, y ->
            velocities = velocities.map { velocity ->
                PointF(
                    (velocity.x - x * 0.05f * speedMultiplier).coerceIn(-5f, 5f),
                    (velocity.y + y * 0.05f * speedMultiplier).coerceIn(-5f, 5f)
                )
            }
        }
        sensorHandler.startListening()
        onDispose {
            sensorHandler.stopListening()
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                val updatedPositions = positions.mapIndexed { index, pos ->
                    val newX = (pos.x + velocities[index].x).coerceIn(
                        0f,
                        screenWidthPx - size.toPx(density)
                    )
                    val newY = (pos.y + velocities[index].y).coerceIn(
                        0f,
                        screenHeightPx - size.toPx(density)
                    )
                    PointF(newX, newY)
                }

                val (resolvedPositions, resolvedVelocities) = motionSyncResolveCollisionsAndAdjustVelocities(
                    updatedPositions,
                    velocities,
                    screenWidthPx,
                    screenHeightPx,
                    size.toPx(density)
                )

                positions = resolvedPositions
                velocities = resolvedVelocities

            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        positions.forEachIndexed { index, pos ->
            Box(
                modifier = Modifier
                    .offset(x = (pos.x / density).dp, y = (pos.y / density).dp)
                    .size(size)
                    .align(Alignment.Center)
            ) {
                composableList[index].invoke()
            }
        }
    }
}

