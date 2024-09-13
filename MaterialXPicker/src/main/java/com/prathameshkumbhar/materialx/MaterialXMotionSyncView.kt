package com.prathameshkumbhar.materialx

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import com.prathameshkumbhar.materialx.utils.MotionSyncSensorHandler


class MotionSyncView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private val sensorHandler = MotionSyncSensorHandler(context)
    private val paint = Paint()

    private val positions = mutableListOf<PointF>()
    private val velocities = mutableListOf<PointF>()
    private val pointFPool = mutableListOf<PointF>()

    private var sizePx: Float = dpToPx(50f)
    private var spacingPx: Float = dpToPx(8f)
    private var speedMultiplier: Float = 1.0f

    init {
        sensorHandler.onSensorChanged = { x, y ->
            handleSensorChange(x, y)
        }
    }

    fun setup(
        size: Float,
        spacing: Float,
        speedMultiplier: Float
    ) {
        this.sizePx = dpToPx(size)
        this.spacingPx = dpToPx(spacing)
        this.speedMultiplier = speedMultiplier

        requestLayout() // Trigger layout pass
    }

    private fun handleSensorChange(x: Float, y: Float) {
        val updatedVelocities = velocities.map { velocity ->
            getPointF().apply {
                (velocity.x - x * 0.05f * speedMultiplier).coerceIn(-5f, 5f)
                (velocity.y + y * 0.05f * speedMultiplier).coerceIn(-5f, 5f)
            }
        }

        val updatedPositions = positions.mapIndexed { index, pos ->
            getPointF().apply {
                (pos.x + updatedVelocities[index].x).coerceIn(
                    0f,
                    (width - sizePx)
                )
                (pos.y + updatedVelocities[index].y).coerceIn(
                    0f,
                    (height - sizePx)
                )
            }
        }

        positions.clear()
        positions.addAll(updatedPositions)
        velocities.clear()
        velocities.addAll(updatedVelocities)

        invalidate()
    }

    override fun onLayout(p0: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val childCount = childCount

        positions.clear()
        velocities.clear()

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val params = child.layoutParams as MarginLayoutParams

            val position = getPointF().apply {
                x = params.leftMargin.toFloat()
                y = params.topMargin.toFloat()
            }
            positions.add(position)

            velocities.add(getPointF().apply {
                x = 0f
                y = 0f
            })

            child.layout(
                params.leftMargin,
                params.topMargin,
                params.leftMargin + child.measuredWidth,
                params.topMargin + child.measuredHeight
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val pos = positions[i]
            child.draw(canvas)
            child.layout(
                pos.x.toInt(),
                pos.y.toInt(),
                (pos.x + child.measuredWidth).toInt(),
                (pos.y + child.measuredHeight).toInt()
            )
        }
    }

    private fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    private fun getPointF(): PointF {
        return if (pointFPool.isNotEmpty()) {
            pointFPool.removeAt(pointFPool.size - 1)
        } else {
            PointF()
        }
    }

    fun startListening() {
        sensorHandler.startListening()
    }

    fun stopListening() {
        sensorHandler.stopListening()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pointFPool.addAll(positions)
        pointFPool.addAll(velocities)
        positions.clear()
        velocities.clear()
    }
}