package com.emikhalets.medialib.presentation.core

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
fun AppDetailsSection(header: String, content: String) {
    if (content.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
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