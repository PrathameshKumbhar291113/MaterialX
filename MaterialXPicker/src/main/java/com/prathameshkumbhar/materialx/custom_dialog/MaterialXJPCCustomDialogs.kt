package com.prathameshkumbhar.materialx.custom_dialog

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun MaterialXJPCCustomDialogs(
    imageSource: Any? = null,
    imageSourceModifier: Modifier = Modifier,
    imageContentDescription: String? = "",
    imageContentScale: ContentScale = ContentScale.Crop,
    backgroundColor: Color = Color.White,
    title: String? = null,
    titleModifier: Modifier = Modifier,
    titleFontFamily: FontFamily? = null,
    titleFontSize: Int = 20,
    description: String? = null,
    descriptionModifier: Modifier = Modifier,
    descriptionFontFamily: FontFamily? = null,
    descriptionFontSize: Int = 16,
    positiveButtonText: String? = null,
    positiveButtonModifier: Modifier = Modifier,
    negativeButtonText: String? = null,
    negativeButtonModifier: Modifier = Modifier,
    isCancelable: Boolean = true,
    dialogModifier: Modifier = Modifier,
    onPositiveClick: (() -> Unit)? = null,
    onNegativeClick: (() -> Unit)? = null
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                if (isCancelable) showDialog.value = false
            },
            title = {
                if (title != null) {
                    Text(
                        text = title,
                        fontFamily = titleFontFamily,
                        fontSize = titleFontSize.sp,
                        modifier = titleModifier.padding(vertical = 8.dp)
                    )
                }
            },
            text = {
                Column(modifier = dialogModifier.background(backgroundColor)) {
                    imageSource?.let {
                        when (it) {
                            is String -> {

                                Image(
                                    painter = rememberAsyncImagePainter(it),
                                    contentDescription = imageContentDescription,
                                    contentScale = imageContentScale,
                                    modifier = imageSourceModifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .padding(bottom = 8.dp)
                                )

                            }

                            is Drawable -> {
                                Image(
                                    painter = rememberAsyncImagePainter(it),
                                    contentDescription = imageContentDescription,
                                    contentScale = imageContentScale,
                                    modifier = imageSourceModifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .padding(bottom = 8.dp)
                                )
                            }
                        }
                    }

                    if (description != null) {
                        Text(
                            text = description,
                            fontFamily = descriptionFontFamily,
                            fontSize = descriptionFontSize.sp,
                            modifier = descriptionModifier.padding(vertical = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                if (positiveButtonText != null) {
                    Button(
                        onClick = {
                            onPositiveClick?.invoke()
                            showDialog.value = false
                        },
                        modifier = positiveButtonModifier
                    ) {
                        Text(positiveButtonText)
                    }
                }
            },
            dismissButton = {
                if (negativeButtonText != null) {
                    TextButton(
                        onClick = {
                            onNegativeClick?.invoke()
                            showDialog.value = false
                        },
                        modifier = negativeButtonModifier
                    ) {
                        Text(negativeButtonText)
                    }
                }
            },
            modifier = dialogModifier
        )
    }
}