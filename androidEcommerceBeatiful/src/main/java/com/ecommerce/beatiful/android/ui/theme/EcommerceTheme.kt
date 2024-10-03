package com.ecommerce.beatiful.android.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    SideEffect {
        val window =  (view.context as Activity).window
        window.statusBarColor =  colors.primary.toArgb()
    }
//    val colors = if (darkTheme) {
//        darkColorScheme(
//            primary = Color(0xFFFFFFFF),
//            secondary = Color(0xFF03DAC5),
//            tertiary = Color(0xFF3700B3)
//        )
//    } else {
//        lightColorScheme(
//            primary = Color(0xFF6200EE),
//            secondary = Color(0xFF03DAC5),
//            tertiary = Color(0xFF3700B3)
//        )
//    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = fontsOpenSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
