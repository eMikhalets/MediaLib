package com.emikhalets.medialib.presentation.core

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppDialog(
    onDismiss: () -> Unit,
    label: String = "",
    padding: Dp = 8.dp,
    cancelable: Boolean = false,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = !cancelable,
            dismissOnClickOutside = !cancelable,
            usePlatformDefaultWidth = false
        ),
    ) {
        DialogLayout(label, padding, content)
    }
}

@Composable
private fun DialogLayout(label: String, padding: Dp, content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(40.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(Modifier.padding(padding)) {
            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )
            }
            content()
        }
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    AppDialog(
        label = stringResource(id = R.string.dialog_delete_title),
        onDismiss = { onDismiss() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = { onDeleteClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        }
    }
}

@Composable
fun PosterDialog(
    poster: String?,
    onDismiss: () -> Unit,
    onOkClick: (String) -> Unit,
) {
    var editPoster by remember { mutableStateOf(poster ?: "") }

    AppDialog(
        label = stringResource(id = R.string.dialog_poster_title),
        onDismiss = { onDismiss() }
    ) {
        AppTextField(value = editPoster, onValueChange = { editPoster = it })
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = { onOkClick(editPoster) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        }
    }
}

@Composable
fun YearDialog(
    year: Int,
    onDismiss: () -> Unit,
    onOkClick: (Int) -> Unit,
) {
    var yearValue by remember {
        mutableStateOf(if (year == 0) Calendar.getInstance().get(Calendar.YEAR) else year)
    }

    AppDialog(onDismiss = onDismiss) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val customView = DatePicker(LocalContext.current, null, R.style.DatePickerSpinnerStyle)
            customView.spinnersShown = true
            customView.calendarViewShown = false
            AndroidView(
                factory = {
                    customView.init(yearValue + 1, 0, 0) { _, _, _, _ -> }
                    customView
                },
                update = { view ->
                    view.setOnDateChangedListener { _, year, _, _ ->
                        yearValue = year
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = { onOkClick(yearValue) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        AppDialog(
            onDismiss = {},
            label = "Preview label"
        ) {
            Text(text = "Preview dialog text")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteDialogPreview() {
    AppTheme {
        DeleteDialog(
            onDismiss = {},
            onDeleteClick = {}
        )
    }
}