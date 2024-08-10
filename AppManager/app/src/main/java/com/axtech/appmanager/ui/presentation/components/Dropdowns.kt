package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axtech.appmanager.ui.theme.DisabledTextColor

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun <T> Dropdown(
    modifier: Modifier = Modifier,
    items: List<T>,
    selected: T? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    error: String? = null,
    placeholder: @Composable () -> Unit = { },
    label: @Composable () -> Unit = { },
    expanded: Boolean = false,
    onExpandChange: ((Boolean) -> Unit)? = null,
    itemText: (T) -> String = { it.toString() },
    itemContent: @Composable (T) -> Unit,
    onItemClick: (T) -> Unit = { },
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var expandedState by remember(expanded) {
        mutableStateOf(expanded)
    }

    fun expandChange(state: Boolean) {
        if (onExpandChange != null) {
            onExpandChange(state)
        } else {
            expandedState = !expandedState
        }
    }

    Column {

        ExposedDropdownMenuBox(expanded = expandedState, onExpandedChange = ::expandChange) {
            InputField(
                modifier = modifier.onFocusChanged {
                    if (it.isFocused) {
                        keyboardController?.hide()
                    }
                },
                enabled = enabled,
                value = TextFieldValue(selected?.let(itemText) ?: ""),
                readOnly = true,
                isError = isError || error != null,
                placeholder = placeholder,
                label = label,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState)
                },
                onValueChange = {},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )

            ExposedDropdownMenu(expanded = expandedState, onDismissRequest = {
                expandChange(false)
            }) {
                items.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onItemClick(item)
                        expandChange(false)
                    }) {
                        itemContent(item)
                    }
                }
            }
        }

        if (error != null) {
            Text(modifier = Modifier.padding(top = 4.dp), text = error, fontSize = 12.sp, color = DisabledTextColor)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun FocusableField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    error: String? = null,
    enabled: Boolean = false,
    value: String = "",
    label: @Composable () -> Unit = {},
    placeholder: @Composable () -> Unit = { },
    focused: Boolean = false,
    onFocusChange: (Boolean) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focused) {
        if (focused) {
            focusRequester.requestFocus()
        }
        else {
            focusRequester.freeFocus()
        }
    }

    InputField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboardController?.hide()
                }
                onFocusChange(it.isFocused)
            },
        enabled = enabled,
        value = value,
        readOnly = true,
        isError = isError,
        error = error,
        placeholder = placeholder,
        label = label,
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = focused)
        },
        onValueChange = {},
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    )
}

@Preview
@Composable
private fun DropdownPreview() {

    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Dropdown(
            items = items,
            expanded = expanded,
            selected = "Item 1",
            placeholder = { Text(text = "Select item") },
            onExpandChange = { expanded = !expanded },
            itemContent = {
                Text(text = it, fontWeight = FontWeight.SemiBold)
            }
        ) {
        }
    }
}