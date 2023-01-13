package com.emikhalets.medialib.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
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

fun Int.formatYear(): Int {
    return if (this == 0) Calendar.getInstance().get(Calendar.YEAR) else this
}

fun String.toDate(pattern: String = "dd.MM.yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

val Int.px: Float
    @Composable
    get() = this * LocalContext.current.resources.displayMetrics.density

inline fun String?.ifNullOrEmpty(defaultValue: () -> String): String =
    if (isNullOrEmpty()) defaultValue() else this

fun String?.toSafeInt(): Int {
    return try {
        this?.toInt() ?: 0
    } catch (ex: NumberFormatException) {
        0
    }
}

fun String?.toIntSafe(): Int {
    return try {
        this?.toInt() ?: 0
    } catch (ex: NumberFormatException) {
        0
    }
}

fun String?.toDoubleSafe(): Double {
    return try {
        this?.toDouble() ?: 0.0
    } catch (ex: NumberFormatException) {
        0.0
    }
}

fun String.toSafeLong(): Long {
    return try {
        this.toLong()
    } catch (ex: NumberFormatException) {
        0L
    }
}

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun getRandomText(words: Int = 500): String {
    return LoremIpsum(words).values.joinToString()
}