package com.emikhalets.medialib.presentation.screens.serials

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.presentation.core.AppDialog
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppSpinner
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToSerialDetails
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.enums.toString

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
        onAddClick = {
            showAddDialog = false
            viewModel.addSerial(it)
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
    onAddClick: (SerialDB) -> Unit,
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
            AddSerialDialog(
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
            text = stringResource(R.string.serials_seasons_value, serial.seasons),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AddSerialDialog(
    onDismiss: () -> Unit,
    onAddClick: (SerialDB) -> Unit,
) {
    val context = LocalContext.current
    val statusListValue = ItemStatus.values().toString(ItemType.SERIAL)

    var title by remember { mutableStateOf("") }
    var titleRu by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf("") }
    var seasons by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(ItemStatus.NONE) }

    val statusList = remember { statusListValue }

    AppDialog(
        label = stringResource(id = R.string.serials_add_title),
        onDismiss = { onDismiss() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.add_new_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = titleRu,
                onValueChange = { titleRu = it },
                label = { Text(stringResource(id = R.string.add_new_title_ru)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.add_new_year)) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = seasons,
                onValueChange = { seasons = it },
                label = { Text(stringResource(R.string.serials_seasons)) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = genres,
                onValueChange = { genres = it },
                label = { Text(stringResource(R.string.app_genres)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(stringResource(R.string.app_comment)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            AppSpinner(
                list = statusList,
                onSelect = { status = ItemStatus.getFromString(context, it, ItemType.SERIAL) }
            )
            IconButton(
                onClick = {
                    onAddClick(
                        SerialDB(
                            title = title,
                            titleRu = titleRu,
                            releaseYear = year.toInt(),
                            comment = comment,
                            genres = genres,
                            seasons = seasons.toInt(),
                            status = status
                        )
                    )
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
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
            onAddClick = {},
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

@Preview(showBackground = true)
@Composable
private fun AddSerialDialogPreview() {
    AppTheme {
        AddSerialDialog(
            onDismiss = {},
            onAddClick = {}
        )
    }
}