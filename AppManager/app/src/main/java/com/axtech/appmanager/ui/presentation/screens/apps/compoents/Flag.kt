package com.axtech.appmanager.ui.presentation.screens.apps.compoents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axtech.appmanager.domain.models.App

@Composable
fun Flag(flag: App.Flag) {
    val (text, color) = when (flag) {
        App.Flag.Production -> Pair("production", Color(0xff18c964))
        App.Flag.Feature -> Pair("feature", Color(0xff0070f0))
        App.Flag.Bugfix -> Pair("bugfix", Color(0xfff31260))
    }

    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        text = text,
        color = Color.White,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    )
}