package com.emikhalets.medialib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            AppTheme {
                AppScaffold(navController, scaffoldState) {
                    AppNavGraph(navController)
                }
            }
        }
    }
}