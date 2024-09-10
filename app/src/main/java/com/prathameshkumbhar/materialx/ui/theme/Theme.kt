package com.prathameshkumbhar.materialx.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val LightColorScheme = lightColorScheme(
    primary = Heliotrope1,
    background = Color.White,
    onPrimary = Heliotrope1,
    onBackground = Color.White
)

@Composable
fun MaterialXJPCTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = Heliotrope1.toArgb()

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}