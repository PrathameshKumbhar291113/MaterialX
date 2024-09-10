package com.prathameshkumbhar.materialx.utils

import android.graphics.PointF
import kotlin.math.pow
import kotlin.math.sqrt


class MotionSyncHelper(){
    fun materialXMotionSyncResolveCollisionsAndAdjustVelocities(
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
}