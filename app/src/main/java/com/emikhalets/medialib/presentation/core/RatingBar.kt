package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emikhalets.medialib.presentation.theme.AppColors.textSecondary
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    onRatingChange: ((Int) -> Unit)? = null,
    maxRating: Int = 5,
    pointSize: Dp = 24.dp,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        repeat(maxRating) {
            val rate = it + 1
            Icon(
                imageVector = Icons.Rounded.Star,
                tint = if (rating >= rate) {
                    MaterialTheme.colors.onBackground
                } else {
                    MaterialTheme.colors.textSecondary
                },
                contentDescription = "",
                modifier = if (onRatingChange != null) {
                    Modifier
                        .size(pointSize)
                        .clickable { onRatingChange(rate) }
                } else {
                    Modifier.size(pointSize)
                }
            )
            if (rate != maxRating) {
                Spacer(modifier = Modifier.width(pointSize / 6))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BarPreview() {
    AppTheme {
        RatingBar(
            rating = 3,
            modifier = Modifier.padding(8.dp),
        )
    }
}