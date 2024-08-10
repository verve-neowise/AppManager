package com.axtech.appmanager.ui.presentation.components

import androidx.compose.ui.text.input.TextFieldValue

data class ValidationField(
    val value: TextFieldValue,
    val error: String? = null
)