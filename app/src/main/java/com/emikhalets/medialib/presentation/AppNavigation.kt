package com.emikhalets.medialib.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.screens.main.MainScreen
import com.emikhalets.medialib.presentation.screens.search.SearchScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {

        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }

        composable(AppScreen.Search.route) {
            SearchScreen(navController)
        }

    }
}

sealed class AppScreen(val route: String, @StringRes val name: Int) {
    object Main : AppScreen("main", R.string.screen_name_main)
    object Search : AppScreen("search", R.string.screen_name_search)

    companion object {
        fun getScreens(): List<AppScreen> {
            return listOf(Main, Search)
        }
    }
}