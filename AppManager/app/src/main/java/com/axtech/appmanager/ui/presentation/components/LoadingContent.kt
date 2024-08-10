package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.axtech.appmanager.ui.theme.Black


@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    content: @Composable () -> Unit
) {
    if (loading) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = Black
            )
        }
    }
    else {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        LoadingContent(
            modifier = Modifier.fillMaxSize(),
            loading = false
        ) {
            Text(text = "Hello Text")
        }
    }
}