package com.emikhalets.medialib.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.support.ViewListItem
import com.emikhalets.medialib.presentation.core.AppIcon
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.navToItemDetails
import com.emikhalets.medialib.presentation.navToItemEdit
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.px
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }
    var itemType by remember { mutableStateOf(ItemType.MOVIE) }

    LaunchedEffect(Unit) {
        viewModel.getMainData()
    }

    MainScreen(
        navController = navController,
        query = query,
        movies = state.movies,
        serials = state.serials,
        books = state.books,
        onAddClick = {
            navController.navToItemEdit(0, itemType)
        },
        onQueryChange = { newQuery ->
            query = newQuery
            viewModel.searchItems(query, itemType)
        },
        onItemTypeChange = { newItemType ->
            itemType = newItemType
        },
        onItemClick = {
            navController.navToItemDetails(it, itemType)
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MainScreen(
    navController: NavHostController,
    query: String,
    movies: List<MovieDB>,
    serials: List<SerialDB>,
    books: List<BookDB>,
    onAddClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onItemTypeChange: (ItemType) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pages = listOf(
        stringResource(id = R.string.pager_tab_movies),
        stringResource(id = R.string.pager_tab_serials),
        stringResource(id = R.string.pager_tab_books)
    )

    LaunchedEffect(pagerState.currentPage) {
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            onItemTypeChange(ItemType.getById(page))
        }
    }

    AppScaffold(navController) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBox(
                query = query,
                onQueryChange = onQueryChange,
                onAddClick = onAddClick
            )

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                }
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    )
                }
            }

            HorizontalPager(count = 3, state = pagerState) { page ->
                val list = when (page) {
                    1 -> serials
                    2 -> books
                    else -> movies
                }
                ItemsList(list = list, onItemClick = onItemClick) { item ->
                    ListItem(item = item, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
private fun SearchBox(
    query: String,
    onQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
    ) {
        AppTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = Icons.Rounded.Search,
            placeholder = stringResource(id = R.string.search_query),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        AppIcon(
            imageVector = Icons.Rounded.Add,
            onClick = { onAddClick() },
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, true)
        )
    }
}

@Composable
private fun ItemsList(
    list: List<ViewListItem>,
    onItemClick: (Int) -> Unit,
    content: @Composable (ViewListItem) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (list.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.empty_list),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(list) { item ->
                    ListItem(item, onItemClick)
                }
            }
        }
    }
}

@Composable
private fun ListItem(item: ViewListItem, onItemClick: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item.id) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.poster)
                .crossfade(true)
                .transformations(RoundedCornersTransformation(8.px))
                .error(R.drawable.ph_poster)
                .build(),
            contentDescription = "",
            placeholder = painterResource(R.drawable.ph_poster),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(70.dp, 95.dp)
                .padding(end = 8.dp)

        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                if (item.status != ItemStatus.NONE && item.status.icon != null) {
                    Icon(imageVector = item.status.icon!!, contentDescription = "")
                }
            }
            if (item is BookDB) {
                Text(
                    text = item.author,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (item is SerialDB) {
                Text(
                    text = stringResource(R.string.seasons_value, item.seasons),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val info = buildString {
                    append(item.releaseYear.toString())
                    if (item.genres.isNotEmpty()) append(", ${item.genres}")
                }
                Text(
                    text = info,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (item.rating > 0) {
                    Row {
                        repeat(5) {
                            Icon(imageVector = Icons.Default.Star,
                                contentDescription = "",
                                tint = if (it < item.rating) Color.Black else Color.Gray,
                                modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MainScreen(
            navController = rememberNavController(),
            query = "",
            movies = listOf(),
            serials = listOf(),
            books = listOf(),
            onAddClick = {},
            onQueryChange = {},
            onItemTypeChange = {},
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    AppTheme {
        ListItem(
            item = MovieDB(
                title = "Movie name",
                genres = "Action, Drama, Action, Drama, Action, Drama",
                releaseYear = 2015,
                rating = 4,
                status = ItemStatus.WATCH
            ),
            onItemClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SerialItemPreview() {
    AppTheme {
        ListItem(
            item = SerialDB(
                title = "Serial name",
                genres = "Action, Drama",
                releaseYear = 2015,
                seasons = 3,
                rating = 3
            ),
            onItemClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookItemPreview() {
    AppTheme {
        ListItem(
            item = BookDB(
                title = "Book name",
                genres = "Drama",
                releaseYear = 1859,
                author = "Sample Author",
                rating = 3,
            ),
            onItemClick = {},
        )
    }
}