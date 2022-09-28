package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun AppScaffold(
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppTopBar() },
        drawerContent = { AppDrawer() }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun AppTopBar() {
    TopAppBar(
        title = { Text("Some title") },
        elevation = 0.dp
    )
}

@Composable
fun ColumnScope.AppDrawer() {
    Text(text = "Some header", fontSize = 24.sp)
    Divider()
    Text(text = "Some item 1")
    Text(text = "Some item 2")
    Text(text = "Some item 3")
    Text(text = "Some item 4")
    Divider()
    Text(text = "Some item 5")
    Text(text = "Some item 6")
    Divider()
    Text(text = "Some item 7")
    Text(text = "Some item 8")
    Text(text = "Some item 9")
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview() {
    AppTheme {
        AppTopBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDrawerPreview() {
    AppTheme {
        Column { AppDrawer() }
    }
}