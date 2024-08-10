package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.axtech.appmanager.ui.theme.BaseBorderColor

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    url: String? = null
) {
    if (url.isNullOrBlank()) {
        Box(modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(Color.LightGray)
            .border(1.dp, BaseBorderColor, RoundedCornerShape(50))
        )
    }
    else {
        AsyncImage(
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(50))
                .border(1.dp, BaseBorderColor, RoundedCornerShape(50)),
            model = url,
            contentDescription = "avatar"
        )
    }
}