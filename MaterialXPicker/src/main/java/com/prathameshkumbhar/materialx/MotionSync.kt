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

import android.graphics.PointF
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prathameshkumbhar.materialx.utils.MaterialXMotionSyncAsset
import com.prathameshkumbhar.materialx.utils.MaterialXMotionSyncContentType
import com.prathameshkumbhar.materialx.utils.MotionSyncSensorHandler
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun MaterialXMotionSyncScreenWithCollisions(assets: List<MaterialXMotionSyncAsset>) {
    val context = LocalContext.current
    val sensorHandler = remember { MotionSyncSensorHandler(context) }

    var positions by remember { mutableStateOf(assets.map { it.initialPosition }) }
    var velocities by remember { mutableStateOf(List(assets.size) { PointF(0f, 0f) }) }

    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val screenHeight = displayMetrics.heightPixels / displayMetrics.density

    DisposableEffect(Unit) {
        sensorHandler.onSensorChanged = { x, y ->
            velocities = velocities.map { velocity ->
                PointF(
                    (velocity.x - x * 0.05f).coerceIn(-5f, 5f),
                    (velocity.y + y * 0.05f).coerceIn(-5f, 5f)
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
                    val newX = (pos.x + velocities[index].x).coerceIn(0f, screenWidth - assets[index].size)
                    val newY = (pos.y + velocities[index].y).coerceIn(0f, screenHeight - assets[index].size)
                    PointF(newX, newY)
                }

                val (resolvedPositions, resolvedVelocities) = materialXMotionSyncResolveCollisionsAndAdjustVelocities(
                    updatedPositions,
                    velocities,
                    screenWidth,
                    screenHeight,
                    assets.map { it.size }
                )

                positions = resolvedPositions
                velocities = resolvedVelocities
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        assets.forEachIndexed { index, asset ->
            MaterialXMotionSyncComponent(
                asset = asset,
                position = positions[index]
            )
        }
    }
}

@Composable
fun MaterialXMotionSyncComponent(asset: MaterialXMotionSyncAsset, position: PointF) {
    Box(
        modifier = Modifier
            .offset(x = position.x.dp, y = position.y.dp)
            .size(asset.size.dp)
            .background(Color.White)
            .border(2.dp, Color.Black)
    ) {
        when (asset.contentType) {
            MaterialXMotionSyncContentType.IMAGE -> {
                asset.iconResId?.let {
                    MaterialXMotionSyncCardContent(asset.title, it)
                }
            }
            MaterialXMotionSyncContentType.TEXT -> {
                Text(
                    text = asset.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            MaterialXMotionSyncContentType.CUSTOM -> {
                asset.customContent?.invoke()
            }
        }
    }
}

@Composable
fun MaterialXMotionSyncCardContent(title: String, iconResId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

private fun materialXMotionSyncResolveCollisionsAndAdjustVelocities(
    positions: List<PointF>,
    velocities: List<PointF>,
    screenWidth: Float,
    screenHeight: Float,
    sizes: List<Float>
): Pair<List<PointF>, List<PointF>> {
    val resolvedPositions = positions.toMutableList()
    val resolvedVelocities = velocities.toMutableList()

    for (i in positions.indices) {
        for (j in i + 1 until positions.size) {
            val posA = resolvedPositions[i]
            val posB = resolvedPositions[j]
            val velocityA = resolvedVelocities[i]
            val velocityB = resolvedVelocities[j]

            val distance = sqrt((posA.x - posB.x).pow(2) + (posA.y - posB.y).pow(2))
            val minDistance = (sizes[i] + sizes[j]) / 2

            if (distance < minDistance) {
                val overlap = minDistance - distance
                val directionX = (posB.x - posA.x) / distance
                val directionY = (posB.y - posA.y) / distance

                resolvedPositions[i] = PointF(
                    posA.x - overlap * directionX / 2,
                    posA.y - overlap * directionY / 2
                ).materialXMotionSyncCoerceInBounds(screenWidth, screenHeight)

                resolvedPositions[j] = PointF(
                    posB.x + overlap * directionX / 2,
                    posB.y + overlap * directionY / 2
                ).materialXMotionSyncCoerceInBounds(screenWidth, screenHeight)

                val newVelocityA = PointF(velocityA.x - directionX * 0.1f, velocityA.y - directionY * 0.1f)
                val newVelocityB = PointF(velocityB.x + directionX * 0.1f, velocityB.y + directionY * 0.1f)

                resolvedVelocities[i] = newVelocityA
                resolvedVelocities[j] = newVelocityB
            }
        }
    }

    return Pair(resolvedPositions, resolvedVelocities)
}

private fun PointF.materialXMotionSyncCoerceInBounds(screenWidth: Float, screenHeight: Float): PointF {
    val xBounded = this.x.coerceIn(0f, screenWidth - 100f)
    val yBounded = this.y.coerceIn(0f, screenHeight - 100f)
    return PointF(xBounded, yBounded)
}
