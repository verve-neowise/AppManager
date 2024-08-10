package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.Button as MaterialButton
import androidx.compose.material.OutlinedButton as MaterialOutlinedButton
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axtech.appmanager.ui.theme.ButtonColors
import com.axtech.appmanager.ui.theme.ButtonShapes
import com.axtech.appmanager.ui.theme.OutlinedButtonColors
import com.axtech.appmanager.ui.theme.SolidButtonColors

@Composable
fun SolidButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: ButtonColors = SolidButtonColors,
    content: @Composable RowScope.() -> Unit
) {
    MaterialButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colors.background,
            contentColor = colors.textColor
        ),
        shape = ButtonShapes.RoundedCorners,
        content = content,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp)
    )
}


@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = OutlinedButtonColors,
    border: BorderStroke = BorderStroke(width = 1.dp, color = colors.borderColor),
    shape: Shape = ButtonShapes.RoundedCorners,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    MaterialOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        border = BorderStroke(width = 1.dp, color = colors.borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colors.textColor,
            backgroundColor = colors.background
        ),
        shape = shape,
        content = content,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp)
    )
}

@Composable
@Preview(backgroundColor = 0xfff, showBackground = true)
private fun ButtonsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(36.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolidButton(onClick = { /*TODO*/ }) {
            Text(text = "Solid Button")
        }
        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "Outlined Button")
        }
    }
}