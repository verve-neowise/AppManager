package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axtech.appmanager.ui.theme.DisabledTextColor
import com.axtech.appmanager.ui.theme.InputTextColors

@Composable
fun Label(text: String) {
    Text(text = text, fontSize = 14.sp)
}

@Composable
fun InputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.OutlinedTextFieldShape,
) {
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = InputTextColors.background,
        unfocusedBorderColor = InputTextColors.borderColor,
        focusedBorderColor = InputTextColors.focusedBorderColor,
        cursorColor = InputTextColors.cursorColor
    )

    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }

    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor, fontSize = 14.sp))
    Column {
        @OptIn(ExperimentalMaterialApi::class)
        BasicTextField(
            value = value,
            modifier = if (label != null) {
                modifier
                    // Merge semantics at the beginning of the modifier chain to ensure padding is
                    // considered part of the text field.
                    .semantics(mergeDescendants = true) {}
                    .padding(top = OutlinedTextFieldTopPadding)
            } else {
                modifier
            }
                .background(colors.backgroundColor(enabled).value, shape)
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.cursorColor(isError).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = @Composable { innerTextField ->

                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = value.text,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError || error != null,
                    interactionSource = interactionSource,
                    colors = colors,
                    border = {

                        TextFieldDefaults.BorderBox(
                            enabled = enabled,
                            isError = isError || error != null,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                        )
                    },
                    contentPadding = PaddingValues(TextFieldPadding)
                )
            }
        )
        if (error != null) {
            Text(modifier = Modifier.padding(top = 4.dp), text = error, fontSize = 12.sp, color = DisabledTextColor)
        }
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.OutlinedTextFieldShape,
) {
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = InputTextColors.background,
        unfocusedBorderColor = InputTextColors.borderColor,
        focusedBorderColor = InputTextColors.focusedBorderColor,
        cursorColor = InputTextColors.cursorColor
    )

    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }

    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor, fontSize = 14.sp))

    Column {
        @OptIn(ExperimentalMaterialApi::class)
        BasicTextField(
            value = value,
            modifier = if (label != null) {
                modifier
                    // Merge semantics at the beginning of the modifier chain to ensure padding is
                    // considered part of the text field.
                    .semantics(mergeDescendants = true) {}
                    .padding(top = OutlinedTextFieldTopPadding)
            } else {
                modifier
            }
                .background(colors.backgroundColor(enabled).value, shape)
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.cursorColor(isError).value),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = @Composable { innerTextField ->

                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = placeholder,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError || error != null,
                    interactionSource = interactionSource,
                    colors = colors,
                    border = {

                        TextFieldDefaults.BorderBox(
                            enabled = enabled,
                            isError = isError || error != null,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                        )
                    },
                    contentPadding = PaddingValues(TextFieldPadding)
                )
            }
        )
        if (error != null) {
            Text(modifier = Modifier.padding(top = 4.dp), text = error, fontSize = 12.sp, color = DisabledTextColor)
        }
    }
}

internal val OutlinedTextFieldTopPadding = 0.dp
internal val TextFieldPadding = 16.dp

@Preview(showBackground = true)
@Composable
private fun InputFieldPreview() {
    var value by remember {
        mutableStateOf(TextFieldValue())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp)
    ) {
        InputField(
            value = value,
            onValueChange = { value = it }
        )
    }
}