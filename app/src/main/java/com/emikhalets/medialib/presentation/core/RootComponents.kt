package com.emikhalets.medialib.presentation.core

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.utils.px

@Composable
fun SearchBox(
    query: String,
    placeholder: String,
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
            leadingIconRes = R.drawable.ic_round_search_24,
            placeholder = placeholder,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f, true)
                .clickable { onAddClick() }
        ) {
            IconPrimary(drawableRes = R.drawable.ic_round_add_24)
        }
    }
}

@Composable
fun AppAsyncImage(
    data: String,
    height: Dp,
    modifier: Modifier = Modifier,
    corners: Float = 8.px,
    onClick: () -> Unit = {},
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .transformations(RoundedCornersTransformation(corners))
            .error(R.drawable.ph_poster)
            .build(),
        contentDescription = "",
        placeholder = painterResource(R.drawable.ph_poster),
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .height(height)
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Composable
fun PosterListItem(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .transformations(RoundedCornersTransformation(8.px))
            .error(R.drawable.ph_poster)
            .build(),
        contentDescription = null,
        placeholder = painterResource(R.drawable.ph_poster),
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .size(70.dp, 95.dp)
            .padding(end = 8.dp)

    )
}

@Composable
fun DetailsSection(
    header: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    if (content.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = header,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = content,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Webview(
    url: String,
    onPageLoaded: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String) {
                        super.onPageFinished(view, url)
                        onPageLoaded(url)
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        })
    }
}