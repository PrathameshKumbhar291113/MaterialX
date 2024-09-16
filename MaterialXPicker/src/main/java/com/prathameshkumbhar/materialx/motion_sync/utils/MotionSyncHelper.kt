package com.prathameshkumbhar.materialx.motion_sync.utils

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
import androidx.compose.ui.unit.Dp
import kotlin.math.pow
import kotlin.math.sqrt

fun motionSyncSetupInitialComponentPositions(
    count: Int,
    layoutDirection: MotionSyncLayout,
    alignmentOption: MotionSyncAlignment,
    size: Dp,
    spacing: Dp,
    screenWidthPx: Float,
    screenHeightPx: Float,
    density: Float
): List<PointF> {
    val sizePx = size.toPx(density)
    val spacingPx = spacing.toPx(density)
    val positions = mutableListOf<PointF>()

    when (layoutDirection) {
        MotionSyncLayout.ROW -> {
            val itemsPerRow = (screenWidthPx / (sizePx + spacingPx)).toInt()
            val numberOfRows = (count + itemsPerRow - 1) / itemsPerRow // Calculate number of rows

            val totalWidth = (sizePx + spacingPx) * itemsPerRow - spacingPx
            val startX = when (alignmentOption) {
                MotionSyncAlignment.LEFT -> 0f
                MotionSyncAlignment.CENTER -> (screenWidthPx - totalWidth) / 2
                MotionSyncAlignment.RIGHT -> screenWidthPx - totalWidth
                else -> 0f
            }
            val startY = when (alignmentOption) {
                MotionSyncAlignment.TOP -> 0f
                MotionSyncAlignment.CENTER -> (screenHeightPx - (sizePx + spacingPx) * numberOfRows) / 2
                MotionSyncAlignment.BOTTOM -> screenHeightPx - (sizePx + spacingPx) * numberOfRows
                else -> 0f
            }

            for (i in 0 until count) {
                val row = i / itemsPerRow
                val column = i % itemsPerRow
                val x = startX + column * (sizePx + spacingPx)
                val y = startY + row * (sizePx + spacingPx)
                positions.add(PointF(x, y))
            }
        }

        MotionSyncLayout.COLUMN -> {
            val itemsPerColumn = (screenHeightPx / (sizePx + spacingPx)).toInt()
            val numberOfColumns =
                (count + itemsPerColumn - 1) / itemsPerColumn // Calculate number of columns

            val totalHeight = (sizePx + spacingPx) * itemsPerColumn - spacingPx
            val startY = when (alignmentOption) {
                MotionSyncAlignment.TOP -> 0f
                MotionSyncAlignment.CENTER -> (screenHeightPx - totalHeight) / 2
                MotionSyncAlignment.BOTTOM -> screenHeightPx - totalHeight
                else -> 0f
            }
            val startX = when (alignmentOption) {
                MotionSyncAlignment.LEFT -> 0f
                MotionSyncAlignment.CENTER -> (screenWidthPx - (sizePx + spacingPx) * numberOfColumns) / 2
                MotionSyncAlignment.RIGHT -> screenWidthPx - (sizePx + spacingPx) * numberOfColumns
                else -> 0f
            }

            for (i in 0 until count) {
                val column = i / itemsPerColumn
                val row = i % itemsPerColumn
                val x = startX + column * (sizePx + spacingPx)
                val y = startY + row * (sizePx + spacingPx)
                positions.add(PointF(x, y))
            }
        }
    }

    return positions
}

fun motionSyncResolveCollisionsAndAdjustVelocities(
    positions: List<PointF>,
    velocities: List<PointF>,
    screenWidth: Float,
    screenHeight: Float,
    sizePx: Float
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

            if (distance < sizePx) {
                val overlap = sizePx - distance
                val directionX = (posB.x - posA.x) / distance
                val directionY = (posB.y - posA.y) / distance

                resolvedPositions[i] = PointF(
                    posA.x - overlap * directionX / 2,
                    posA.y - overlap * directionY / 2
                ).coerceInBounds(screenWidth, screenHeight, sizePx)

                resolvedPositions[j] = PointF(
                    posB.x + overlap * directionX / 2,
                    posB.y + overlap * directionY / 2
                ).coerceInBounds(screenWidth, screenHeight, sizePx)

                resolvedVelocities[i] = PointF(
                    (velocityA.x - directionX * 0.1f).coerceIn(-5f, 5f),
                    (velocityA.y - directionY * 0.1f).coerceIn(-5f, 5f)
                )

                resolvedVelocities[j] = PointF(
                    (velocityB.x + directionX * 0.1f).coerceIn(-5f, 5f),
                    (velocityB.y + directionY * 0.1f).coerceIn(-5f, 5f)
                )
            }
        }
    }

    return Pair(resolvedPositions, resolvedVelocities)
}

private fun PointF.coerceInBounds(screenWidth: Float, screenHeight: Float, sizePx: Float): PointF {
    val xBounded = this.x.coerceIn(0f, screenWidth - sizePx)
    val yBounded = this.y.coerceIn(0f, screenHeight - sizePx)
    return PointF(xBounded, yBounded)
}

fun Dp.toPx(density: Float): Float {
    return this.value * density
}


fun motionSyncSetupInitialComponentPositions(
    count: Int,
    layoutDirection: MotionSyncLayout,
    alignmentOption: MotionSyncAlignment,
    sizePx: Float,
    spacingPx: Float,
    screenWidthPx: Float,
    screenHeightPx: Float
): List<PointF> {
    val positions = mutableListOf<PointF>()

    when (layoutDirection) {
        MotionSyncLayout.ROW -> {
            val itemsPerRow = (screenWidthPx / (sizePx + spacingPx)).toInt()
            val numberOfRows = (count + itemsPerRow - 1) / itemsPerRow

            val totalWidth = (sizePx + spacingPx) * itemsPerRow - spacingPx
            val startX = when (alignmentOption) {
                MotionSyncAlignment.LEFT -> 0f
                MotionSyncAlignment.CENTER -> (screenWidthPx - totalWidth) / 2
                MotionSyncAlignment.RIGHT -> screenWidthPx - totalWidth
                else -> 0f
            }
            val startY = when (alignmentOption) {
                MotionSyncAlignment.TOP ->
                    0f

                MotionSyncAlignment.CENTER ->
                    (screenHeightPx - (sizePx + spacingPx) * numberOfRows) / 2

                MotionSyncAlignment.BOTTOM ->
                    screenHeightPx - (sizePx + spacingPx) * numberOfRows

                else -> 0f
            }

            for (i in 0 until count) {
                val row = i / itemsPerRow
                val column = i % itemsPerRow
                val x = startX + column * (sizePx + spacingPx)
                val y = startY + row * (sizePx + spacingPx)
                positions.add(PointF(x, y))
            }
        }

        MotionSyncLayout.COLUMN -> {
            val itemsPerColumn = (screenHeightPx / (sizePx + spacingPx)).toInt()
            val numberOfColumns = (count + itemsPerColumn - 1) / itemsPerColumn

            val totalHeight = (sizePx + spacingPx) * itemsPerColumn - spacingPx
            val startY = when (alignmentOption) {
                MotionSyncAlignment.TOP -> 0f
                MotionSyncAlignment.CENTER -> (screenHeightPx - totalHeight) / 2
                MotionSyncAlignment.BOTTOM -> screenHeightPx - totalHeight
                else -> 0f
            }
            val startX = when (alignmentOption) {
                MotionSyncAlignment.LEFT -> 0f
                MotionSyncAlignment.CENTER -> (screenWidthPx - (sizePx + spacingPx) * numberOfColumns) / 2
                MotionSyncAlignment.RIGHT -> screenWidthPx - (sizePx + spacingPx) * numberOfColumns
                else -> 0f
            }

            for (i in 0 until count) {
                val column = i / itemsPerColumn
                val row = i % itemsPerColumn
                val x = startX + column * (sizePx + spacingPx)
                val y = startY + row * (sizePx + spacingPx)
                positions.add(PointF(x, y))
            }
        }
    }

    return positions
}

/*fun motionSyncResolveCollisionsAndAdjustVelocities(
    positions: List<PointF>,
    velocities: List<PointF>,
    screenWidth: Float,
    screenHeight: Float,
    sizePx: Float
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

            if (distance < sizePx) {
                val overlap = sizePx - distance
                val directionX = (posB.x - posA.x) / distance
                val directionY = (posB.y - posA.y) / distance

                resolvedPositions[i] = PointF(posA.x - directionX * overlap / 2, posA.y - directionY * overlap / 2)
                resolvedPositions[j] = PointF(posB.x + directionX * overlap / 2, posB.y + directionY * overlap / 2)

                val newVelAX = velocityA.x + directionX * 0.5f
                val newVelAY = velocityA.y + directionY * 0.5f
                val newVelBX = velocityB.x - directionX * 0.5f
                val newVelBY = velocityB.y - directionY * 0.5f

                resolvedVelocities[i] = PointF(newVelAX, newVelAY)
                resolvedVelocities[j] = PointF(newVelBX, newVelBY)
            }
        }
    }

    return Pair(resolvedPositions, resolvedVelocities)
}*/

