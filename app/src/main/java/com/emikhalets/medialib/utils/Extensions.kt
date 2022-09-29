package com.emikhalets.medialib.utils

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <T : Any> execute(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline block: suspend () -> T,
): Result<T> {
    return withContext(dispatcher) {
        runCatching { block() }.onFailure { it.printStackTrace() }
    }
}

fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    content: @Composable (value: T?) -> Unit,
) {
    items(lazyPagingItems.itemCount) { index ->
        content(lazyPagingItems[index])
    }
}

fun buildMovieImage(path: String?): String {
    return "https://image.tmdb.org/t/p/w500/$path"
}