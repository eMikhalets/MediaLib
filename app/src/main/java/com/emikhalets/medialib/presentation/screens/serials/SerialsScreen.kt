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
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToSerialDetails
import com.emikhalets.medialib.presentation.navToSerialEdit
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SerialsScreen(
    navController: NavHostController,
    viewModel: SerialsViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getSerials(query)
    }

    SerialsScreen(
        navController = navController,
        query = query,
        serials = viewModel.state.serials,
        onAddClick = {
            navController.navToSerialEdit(null)
        },
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
    onAddClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSerialClick: (Int) -> Unit,
) {
    AppScaffold(navController) {
        RootScreenList(
            query = query,
            list = serials,
            searchPlaceholder = stringResource(R.string.movies_query_placeholder),
            onAddClick = onAddClick,
            onQueryChange = onQueryChange,
            onItemClick = onSerialClick
        ) { item ->
            SerialItem(item as SerialDB, onSerialClick)
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
            text = stringResource(R.string.serials_seasons_value, serial.seasons),
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
            onAddClick = {},
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
                title = "Long long Spider-man",
                genres = "Action, Drama",
                releaseYear = 2015,
                seasons = 3,
                rating = 3
            ),
            onSerialClick = {},
        )
    }
}