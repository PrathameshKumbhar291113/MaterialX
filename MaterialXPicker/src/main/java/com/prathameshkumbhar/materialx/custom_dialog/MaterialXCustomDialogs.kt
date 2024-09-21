package com.prathameshkumbhar.materialx.custom_dialog

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

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.prathameshkumbhar.materialx.custom_dialog.MaterialXCustomDialogs.MaterialXCustomDialogBuilder.Companion.MATCH_PARENT
import com.prathameshkumbhar.materialx.custom_dialog.MaterialXCustomDialogs.MaterialXCustomDialogBuilder.Companion.WRAP_CONTENT
import com.prathameshkumbhar.materialxpicker.databinding.MaterialXCustomDialogBoxBinding

class MaterialXCustomDialogs private constructor(
    private val builder: MaterialXCustomDialogBuilder
) {
    private val dialog: Dialog = Dialog(builder.context)
    private val binding: MaterialXCustomDialogBoxBinding =
        MaterialXCustomDialogBoxBinding.inflate(LayoutInflater.from(builder.context))

    init {
        dialog.setContentView(binding.root)
        dialog.setCancelable(builder.isCancelable)

        setupDialog()
    }

    private fun setupDialog() {
        // Set background
        binding.dialogContainer.background = builder.backgroundDrawable
        builder.backgroundColor?.let {
            binding.dialogContainer.setBackgroundColor(it)
        }

        // Set padding
        binding.dialogContainer.setPadding(
            builder.paddingStart ?: binding.dialogContainer.paddingStart,
            builder.paddingTop ?: binding.dialogContainer.paddingTop,
            builder.paddingEnd ?: binding.dialogContainer.paddingEnd,
            builder.paddingBottom ?: binding.dialogContainer.paddingBottom
        )

        // Set image with dynamic size and scale
        builder.imageDrawable?.let {
            binding.ivImage.apply {
                setImageDrawable(it)
                isVisible = true

                // Apply dynamic width and height (support for 0dp)
                val layoutParams = this.layoutParams
                layoutParams.width = if (builder.imageWidth == 0) 0 else when (builder.imageWidth) {
                    WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
                    MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
                    else -> builder.imageWidth
                }
                layoutParams.height = if (builder.imageHeight == 0) 0 else when (builder.imageHeight) {
                    WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
                    MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
                    else -> builder.imageHeight
                }
                this.layoutParams = layoutParams

                // Apply scale type if specified
                builder.imageScaleType?.let { scaleType ->
                    this.scaleType = scaleType
                }
            }
        } ?: run {
            binding.ivImage.isVisible = false
        }

        // Set title
        binding.tvTitle.apply {
            text = builder.title ?: ""
            typeface = builder.titleFont
            isVisible = builder.title != null
        }

        // Set description
        binding.tvDescription.apply {
            text = builder.description ?: ""
            typeface = builder.descriptionFont
            isVisible = builder.description != null
        }

        // Set buttons
        binding.btnPositive.apply {
            text = builder.positiveButtonText ?: ""
            typeface = builder.positiveButtonFont
            isVisible = builder.positiveButtonText != null
            setOnClickListener {
                builder.onPositiveClick?.invoke()
                dialog.dismiss()
            }
        }

        binding.btnNegative.apply {
            text = builder.negativeButtonText ?: ""
            typeface = builder.negativeButtonFont
            isVisible = builder.negativeButtonText != null
            setOnClickListener {
                builder.onNegativeClick?.invoke()
                dialog.dismiss()
            }
        }
    }

    fun show() {
        dialog.show()
    }

    class MaterialXCustomDialogBuilder(val context: Context) {
        var backgroundDrawable: Drawable? = null
        var backgroundColor: Int? = null
        var imageDrawable: Drawable? = null
        var imageWidth: Int = WRAP_CONTENT // Default to wrap content
        var imageHeight: Int = WRAP_CONTENT // Default to wrap content
        var imageScaleType: ImageView.ScaleType? = null
        var title: String? = null
        var description: String? = null
        var positiveButtonText: String? = null
        var negativeButtonText: String? = null
        var isCancelable: Boolean = true

        var titleFont: Typeface? = null
        var descriptionFont: Typeface? = null
        var positiveButtonFont: Typeface? = null
        var negativeButtonFont: Typeface? = null

        var paddingStart: Int? = null
        var paddingTop: Int? = null
        var paddingEnd: Int? = null
        var paddingBottom: Int? = null

        var onPositiveClick: (() -> Unit)? = null
        var onNegativeClick: (() -> Unit)? = null

        // Constants for wrap content, match parent, and 0dp
        companion object {
            const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
            const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
        }

        // Builder functions for customization
        fun setBackgroundColor(color: Int) = apply { this.backgroundColor = color }
        fun setBackgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }
        fun setImageDrawable(drawable: Drawable) = apply { this.imageDrawable = drawable }
        fun setImageSize(width: Int, height: Int) = apply {
            this.imageWidth = width
            this.imageHeight = height
        }
        fun setImageScaleType(scaleType: ImageView.ScaleType) = apply { this.imageScaleType = scaleType }
        fun setTitle(title: String) = apply { this.title = title }
        fun setDescription(description: String) = apply { this.description = description }
        fun setPositiveButton(text: String, onClick: (() -> Unit)? = null) =
            apply { this.positiveButtonText = text; this.onPositiveClick = onClick }

        fun setNegativeButton(text: String, onClick: (() -> Unit)? = null) =
            apply { this.negativeButtonText = text; this.onNegativeClick = onClick }

        fun setCancelable(cancelable: Boolean) = apply { this.isCancelable = cancelable }
        fun setTitleFont(font: Typeface) = apply { this.titleFont = font }
        fun setDescriptionFont(font: Typeface) = apply { this.descriptionFont = font }
        fun setPositiveButtonFont(font: Typeface) = apply { this.positiveButtonFont = font }
        fun setNegativeButtonFont(font: Typeface) = apply { this.negativeButtonFont = font }

        fun setPadding(start: Int, top: Int, end: Int, bottom: Int) = apply {
            this.paddingStart = start
            this.paddingTop = top
            this.paddingEnd = end
            this.paddingBottom = bottom
        }

        fun build(): MaterialXCustomDialogs = MaterialXCustomDialogs(this)
    }
}
