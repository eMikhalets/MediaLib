package com.emikhalets.medialib.presentation.core

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme


@Composable
fun SearchBox(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        AppTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIconRes = R.drawable.ic_round_search_24,
            placeholder = placeholder,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, true)
                .padding(start = 8.dp)
                .clickable { onAddClick() }
        ) {
            IconPrimary(drawableRes = R.drawable.ic_round_add_24)
        }
    }
}

@Composable
fun AppLoader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DetailsSection(
    header: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    if (content.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            TextTitle(
                text = header,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            TextPrimary(
                text = content,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DetailsSectionList(
    header: String,
    content: List<String>,
    modifier: Modifier = Modifier,
) {
    if (content.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            TextTitle(
                text = header,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            content.forEach {
                TextPrimary(
                    text = it,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Webview(
    url: String,
    modifier: Modifier = Modifier,
    onPageStarted: ((String) -> Unit) = {},
    onPageFinished: ((String) -> Unit) = {},
) {
    val context = LocalContext.current

    Box(modifier = modifier) {
        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        url?.let(onPageStarted)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        url?.let(onPageFinished)
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBoxPreview() {
    AppTheme {
        SearchBox(
            query = "",
            placeholder = "Test text",
            onQueryChange = {},
            onAddClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}