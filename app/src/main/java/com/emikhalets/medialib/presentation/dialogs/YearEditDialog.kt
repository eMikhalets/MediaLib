package com.emikhalets.medialib.presentation.dialogs

import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.formatYear
import java.util.*

@Composable
fun YearEditDialog(
    year: Int,
    onDismiss: () -> Unit,
    onSaveClick: (year: Int) -> Unit,
) {
    var yearValue by remember { mutableStateOf(year.formatYear()) }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        DialogLayout(
            yearValue = yearValue,
            onYearChanged = { yearValue = it },
            onCancelClick = onDismiss,
            onSaveClick = { onSaveClick(yearValue) }
        )
    }
}

// TODO: show only year in spinner
@Suppress("DEPRECATION")
@Composable
private fun DialogLayout(
    yearValue: Int,
    onYearChanged: (Int) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
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
                view.setOnDateChangedListener { _, year, _, _ -> onYearChanged(year) }
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            TextButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.dialog_cancel))
            }
            TextButton(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.dialog_save))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        DialogLayout(
            yearValue = 1234,
            onYearChanged = {},
            onCancelClick = {},
            onSaveClick = {}
        )
    }
}