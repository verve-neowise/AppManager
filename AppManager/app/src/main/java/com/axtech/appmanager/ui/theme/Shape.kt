package com.axtech.appmanager.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

object ButtonShapes {
    val SmallRoundedCorners = RoundedCornerShape(4.dp)
    val MediumRoundedCorners = RoundedCornerShape(8.dp)
    val RoundedCorners = RoundedCornerShape(12.dp)
}