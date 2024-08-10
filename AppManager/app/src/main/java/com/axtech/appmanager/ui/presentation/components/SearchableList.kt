package com.axtech.appmanager.ui.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axtech.appmanager.ui.theme.AccentColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SearchableListState<T>(
    val query: String = "",
    val items: List<T> = listOf()
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchableList(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: suspend (String) -> Unit = {},
    loading: Boolean = false,
    content: LazyListScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()

    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier) {
        InputField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester),
            value = value,
            onValueChange = {
                scope.launch {
                    onValueChange(it)
                }
            },
            trailingIcon = {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 3.dp,
                        color = AccentColor
                    )
                }
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            content = content
        )
    }
}

@Preview(backgroundColor = 0xfff, showBackground = true)
@Composable
private fun SearchableListPreview() {

    val countries = listOf(
        "United States",
        "Canada",
        "United Kingdom",
        "Australia",
        "Germany",
        "France",
        "Italy",
        "Spain",
        "Japan",
        "China",
        "India",
        "Brazil",
        "Mexico",
        "South Korea",
        "Russia",
        "Argentina",
        "South Africa",
        "Egypt",
        "Nigeria",
        "Kenya",
        "Saudi Arabia",
        "Turkey",
        "Greece",
        "Sweden",
        "Norway",
        "Switzerland",
        "Netherlands",
        "Belgium",
        "Ireland"
    )

    var value by remember {
        mutableStateOf("")
    }

    var items by remember {
        mutableStateOf(countries)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchableList(
            modifier = Modifier.fillMaxSize(),
            loading = isLoading,
            value = value,
            onValueChange = { newValue ->
                value = newValue
                isLoading = true
                delay(2000)
                items = countries.filter { it.contains(newValue) }
                isLoading = false
            },
        ) {
            items(items) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = it
                )
            }
        }
    }
}

