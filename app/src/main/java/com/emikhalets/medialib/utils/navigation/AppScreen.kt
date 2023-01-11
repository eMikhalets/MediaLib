package com.emikhalets.medialib.utils.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.NavArgs


enum class AppScreen(val route: String) {

    MainMenu("main_menu"),
    Movies("movies"),
    Serials("serials");

//    object Main : AppScreen(
//        route = "main",
//        titleRes = R.string.screen_name_main,
//        icon = Icons.Rounded.Home
//    )
//
//    object Search : AppScreen(
//        route = "search",
//        titleRes = R.string.screen_name_search,
//        icon = Icons.Rounded.Search
//    )
//
//    object SearchWebView : AppScreen(
//        route = "search_webview/{${NavArgs.SEARCH_TYPE}}",
//        titleRes = R.string.screen_name_search,
//    )
//
//    object Details : AppScreen(
//        route = "details/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
//        titleRes = R.string.screen_name_details
//    )
//
//    object Edit : AppScreen(
//        route = "edit/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
//        titleRes = R.string.screen_name_edit
//    )
//
//    companion object {
//
//        @Composable
//        fun getTitle(entry: NavBackStackEntry?): String {
//            return when (entry?.destination?.route) {
//                Main.route -> stringResource(Main.titleRes)
//                Search.route -> stringResource(Search.titleRes)
//                else -> ""
//            }
//        }
//
//        fun isRootScreen(entry: NavBackStackEntry?): Boolean {
//            val route = entry?.destination?.route
//            return route == Main.route || route == Search.route
//        }
//    }
}