package com.emikhalets.medialib.presentation.screens.serials

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.presentation.core.AddItemDialog
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToSerialDetails
import com.emikhalets.medialib.presentation.theme.AppTheme
import java.util.*

@Composable
fun SerialsScreen(
    navController: NavHostController,
    viewModel: SerialsViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSerials(query)
    }

    SerialsScreen(
        navController = navController,
        query = query,
        serials = viewModel.state.serials,
        showAddDialog = showAddDialog,
        onAddClick = { name, year, comment ->
            showAddDialog = false
            viewModel.addSerial(name, year, comment)
        },
        onAddDialogVisible = { showAddDialog = it },
        onQueryChange = {
            query = it
            viewModel.getSerials(query)
        },
        onSerialClick = {
            navController.navToSerialDetails(it)
        },
    )
}

@Composable
private fun SerialsScreen(
    navController: NavHostController,
    query: String,
    serials: List<SerialDB>,
    showAddDialog: Boolean,
    onAddClick: (String, String, String) -> Unit,
    onAddDialogVisible: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onSerialClick: (Int) -> Unit,
) {
    AppScaffold(navController) {
        RootScreenList(
            query = query,
            list = serials,
            searchPlaceholder = stringResource(R.string.movies_query_placeholder),
            onAddClick = { onAddDialogVisible(true) },
            onQueryChange = onQueryChange,
            onItemClick = onSerialClick
        ) { item ->
            SerialItem(item as SerialDB, onSerialClick)
        }

        if (showAddDialog) {
            AddItemDialog(
                onDismiss = { onAddDialogVisible(false) },
                onAddClick = onAddClick
            )
        }
    }
}

@Composable
private fun SerialItem(
    serial: SerialDB,
    onSerialClick: (Int) -> Unit,
) {
    RootListItem(
        item = serial,
        onItemClick = onSerialClick
    ) {
        Text(
            text = stringResource(R.string.app_genres_value, serial.genres),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.serials_seasons, serial.seasons),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SerialsScreen(
            navController = rememberNavController(),
            query = "",
            serials = listOf(),
            showAddDialog = false,
            onAddClick = { _, _, _ -> },
            onAddDialogVisible = {},
            onQueryChange = {},
            onSerialClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    AppTheme {
        SerialItem(
            serial = SerialDB(
                id = 0,
                title = "Long long Spider-man",
                genres = "Action, Drama",
                originalTitle = "Original title",
                overview = "",
                poster = "",
                releaseDate = Calendar.getInstance().timeInMillis,
                tagline = "",
                voteAverage = 0.0,
                seasons = 3,
                rating = 3
            ),
            onSerialClick = {},
        )
    }
}