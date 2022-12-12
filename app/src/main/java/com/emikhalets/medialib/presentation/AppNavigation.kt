package com.emikhalets.medialib.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.screens.details.DetailsScreen
import com.emikhalets.medialib.presentation.screens.edit.EditScreen
import com.emikhalets.medialib.presentation.screens.main.MainScreen
import com.emikhalets.medialib.presentation.screens.search.SearchMainScreen
import com.emikhalets.medialib.presentation.screens.search.SearchWebviewScreen
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.enums.SearchType

sealed class AppScreen(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector? = null,
) {

    object Main : AppScreen(
        route = "main",
        titleRes = R.string.screen_name_main,
        icon = Icons.Rounded.Home
    )

    object Search : AppScreen(
        route = "search",
        titleRes = R.string.screen_name_search,
        icon = Icons.Rounded.Search
    )

    object SearchWebView : AppScreen(
        route = "search_webview/{${NavArgs.SEARCH_TYPE}}",
        titleRes = R.string.screen_name_search,
    )

    object Details : AppScreen(
        route = "details/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
        titleRes = R.string.screen_name_details
    )

    object Edit : AppScreen(
        route = "edit/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
        titleRes = R.string.screen_name_edit
    )

    companion object {

        @Composable
        fun getTitle(entry: NavBackStackEntry?): String {
            return when (entry?.destination?.route) {
                Main.route -> stringResource(Main.titleRes)
                Search.route -> stringResource(Search.titleRes)
                else -> ""
            }
        }

        fun isRootScreen(entry: NavBackStackEntry?): Boolean {
            val route = entry?.destination?.route
            return route == Main.route || route == Search.route
        }
    }
}

object NavArgs {
    const val ITEM_ID = "nav_arg_item_id"
    const val ITEM_TYPE = "nav_arg_item_type"
    const val SEARCH_TYPE = "nav_arg_search_type"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {

        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }

        composable(AppScreen.Search.route) {
            SearchMainScreen(navController)
        }

        composable(
            route = AppScreen.SearchWebView.route,
            arguments = listOf(navArgument(NavArgs.SEARCH_TYPE) { type = NavType.StringType })) {
            val type = it.arguments?.getString(NavArgs.SEARCH_TYPE)
            SearchWebviewScreen(
                navController = navController,
                type = SearchType.valueOf(type ?: SearchType.MOVIE.toString())
            )
        }

        composable(
            route = AppScreen.Details.route,
            arguments = listOf(
                navArgument(NavArgs.ITEM_ID) { type = NavType.IntType },
                navArgument(NavArgs.ITEM_TYPE) { type = NavType.StringType }
            )) {
            val id = it.arguments?.getInt(NavArgs.ITEM_ID)
            val type = it.arguments?.getString(NavArgs.ITEM_TYPE)
            DetailsScreen(
                navController = navController,
                itemId = id,
                itemType = ItemType.valueOf(type ?: ItemType.MOVIE.toString())
            )
        }

        composable(
            route = AppScreen.Edit.route,
            arguments = listOf(
                navArgument(NavArgs.ITEM_ID) { type = NavType.IntType },
                navArgument(NavArgs.ITEM_TYPE) { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getInt(NavArgs.ITEM_ID)
            val type = it.arguments?.getString(NavArgs.ITEM_TYPE)
            EditScreen(
                navController = navController,
                itemId = id ?: 0,
                itemType = ItemType.valueOf(type ?: ItemType.MOVIE.toString())
            )
        }
    }
}

fun NavHostController.navToItemDetails(id: Int, itemType: ItemType) {
    navigate("details/$id/$itemType")
}

fun NavHostController.navToItemEdit(id: Int, itemType: ItemType) {
    navigate("edit/$id/$itemType")
}

fun NavHostController.navToSearching(searchType: SearchType) {
    navigate("search_webview/$searchType")
}