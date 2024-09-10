package com.prathameshkumbhar.materialx

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.prathameshkumbhar.materialx.utils.MotionSyncHelper
import com.prathameshkumbhar.materialx.utils.MotionSyncSensorHandler

class MotionSyncView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val sensorHandler = MotionSyncSensorHandler(context)
    private val positions = mutableListOf<PointF>()
    private val velocities = mutableListOf<PointF>()
    private val sizes = mutableListOf<Float>()

    init {
        sensorHandler.onSensorChanged = { x, y ->
            velocities.forEachIndexed { index, velocity ->
                velocities[index] = PointF(
                    (velocity.x - x * 0.05f).coerceIn(-5f, 5f),
                    (velocity.y + y * 0.05f).coerceIn(-5f, 5f)
                )
            }
            updatePositions()
        }
        sensorHandler.startListening()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        children.forEach {
            positions.add(PointF(it.x, it.y))
            velocities.add(PointF(0f, 0f))
            sizes.add(it.width.toFloat())
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        sensorHandler.stopListening()
    }

    private fun updatePositions() {
        val screenWidth = width.toFloat()
        val screenHeight = height.toFloat()

        for (i in positions.indices) {
            val pos = positions[i]
            val velocity = velocities[i]

            val newX = (pos.x + velocity.x).coerceIn(0f, screenWidth - sizes[i])
            val newY = (pos.y + velocity.y).coerceIn(0f, screenHeight - sizes[i])
            positions[i] = PointF(newX, newY)
        }

        val (resolvedPositions, resolvedVelocities) = MotionSyncHelper().materialXMotionSyncResolveCollisionsAndAdjustVelocities(
            positions,
            velocities,
            screenWidth,
            screenHeight,
            sizes
        )

        positions.clear()
        positions.addAll(resolvedPositions)
        velocities.clear()
        velocities.addAll(resolvedVelocities)

        children.forEachIndexed { index, child ->
            child.x = positions[index].x
            child.y = positions[index].y
        }

        invalidate()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
    }
}
