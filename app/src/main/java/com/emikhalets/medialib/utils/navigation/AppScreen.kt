package com.emikhalets.medialib.utils.navigation

import com.emikhalets.medialib.R


enum class AppScreen(val route: String) {

    Library("library"),
    Searching("searching"),
    Movies("movies"),
    MovieDetails("movie_details"),
    MovieEdit("movie_edit"),
    Serials("serials"),
    SerialDetails("serial_details"),
    SerialEdit("serial_edit");

    companion object {

        fun getBottomBarItems(): List<AppScreen> {
            return listOf()
        }

        fun AppScreen.getBottomBarIconRes(): Int {
            return when (this) {
                Library -> R.drawable.ic_round_home_24
                Searching -> R.drawable.ic_round_search_24
                else -> 0
            }
        }

        fun AppScreen.getBottomBarTextRes(): Int {
            return when (this) {
                Library -> R.string.screen_title_library
                Searching -> R.string.screen_title_searching
                else -> 0
            }
        }
    }

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