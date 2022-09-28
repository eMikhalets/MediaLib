package com.emikhalets.medialib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.screens.main.MainScreen
import com.emikhalets.medialib.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AppScaffold {
                    MainScreen()
                }
            }
        }
    }
}