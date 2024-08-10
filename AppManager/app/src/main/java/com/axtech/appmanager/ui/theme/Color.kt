package com.axtech.appmanager.ui.theme

import androidx.compose.ui.graphics.Color

val Gray = Color(0xFF999999)
val Red = Color(0xFFFF6666)
val Blue = Color(0xFF4444FF)
val Green = Color(0xFF44aa44)
val Black = Color(0xff000000)
val Background = Color(0xff1F1F22)
val LightBlack = Black.copy(0.5f)

val AccentColor = Color(0xFF0070f0)
val DisabledWhite = Color.White.copy(alpha = 0.4f)
val SurfaceBackground = Color(0xFFFFFFFF)
val StatusBarBackground = Black

val BaseTextColor = Black
val DisabledTextColor = Gray
val BaseBorderColor = Color(0xFFEEEEEE)

object InputTextColors {
    val background = Color(0xFFFAFAFA)
    val focusedBackground = AccentColor.copy(alpha = 0.5f)
    val borderColor = BaseBorderColor
    val focusedBorderColor = AccentColor
    val cursorColor = AccentColor
}

interface ButtonColors {
    val background: Color
    val textColor: Color
    val borderColor: Color
}

object ActionButtonColors {
    val background: Color = Black.copy(alpha = 0.05f)
    val tint: Color = Black.copy(alpha = 0.7f)
}

object SolidButtonColors : ButtonColors {
    override val background = Color(0xFF262626)
    override val textColor = Color(0xFFEEEEEE)
    override val borderColor = Color(0xFF262626)
}

object OutlinedButtonColors : ButtonColors {
    override val background = Color(0xFFFAFAFA)
    override val textColor = Color(0xFF262626)
    override val borderColor = Color(0xFFEEEEEE)
}

object SecondAccentButtonColors : ButtonColors {
    override val background = Color.Transparent
    override val textColor = AccentColor
    override val borderColor = AccentColor
}


object PrimaryAccentButtonColors : ButtonColors {
    override val background = AccentColor
    override val textColor = Black
    override val borderColor = AccentColor
}