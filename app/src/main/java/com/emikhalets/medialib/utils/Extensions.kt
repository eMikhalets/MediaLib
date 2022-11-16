package com.emikhalets.medialib.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

suspend inline fun <T : Any> execute(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline block: suspend () -> T,
): Result<T> {
    return withContext(dispatcher) {
        runCatching { block() }.onFailure { it.printStackTrace() }
    }
}

fun Long.formatDate(pattern: String = "dd.MM.yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun String.toDate(pattern: String = "dd.MM.yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

val Int.px: Float
    @Composable
    get() = this * LocalContext.current.resources.displayMetrics.density
