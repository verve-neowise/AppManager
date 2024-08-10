package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axtech.appmanager.ui.theme.InputTextColors
import com.axtech.appmanager.ui.theme.Shapes

@Composable
fun CheckableItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    checkedChanged: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .clip(Shapes.small)
            .clickable {
                checkedChanged()
            }
            .background(
                if (checked) InputTextColors.focusedBackground else InputTextColors.background
            )
            .border(1.dp, InputTextColors.borderColor)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
@Preview
fun CheckableItemPreview() {

    var checked by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CheckableItem(
            modifier = Modifier.fillMaxWidth(),
            checked = checked,
            checkedChanged = { checked = !checked }
        ) {
            Column {
                Text(text = "Email", fontWeight = FontWeight.SemiBold)
                Text(text = "Password")
            }
        }
    }
}