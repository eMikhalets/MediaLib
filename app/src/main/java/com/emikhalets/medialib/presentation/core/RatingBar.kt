package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        repeat(maxRating) {
            val rate = it + 1
            Icon(
                imageVector = Icons.Rounded.Star,
                tint = if (rating >= rate) Color.Black else Color.Gray,
                contentDescription = "",
                modifier = Modifier.clickable { onRatingChange(rate) }
            )
            if (rate != maxRating) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun RatingRow(rating: Int) {
    Row {
        repeat(5) {
            Icon(imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = if (it < rating) Color.Black else Color.Gray,
                modifier = Modifier.size(14.dp))
        }
    }
}