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
import com.emikhalets.medialib.presentation.screens.search.SearchMoviesScreen
import com.emikhalets.medialib.utils.enums.ItemType

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
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {

        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }

        composable(AppScreen.Search.route) {
            SearchMoviesScreen(navController)
        }

        composable(
            route = "${AppScreen.Details.route}/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
            arguments = listOf(
                navArgument(NavArgs.ITEM_ID) { type = NavType.IntType },
                navArgument(NavArgs.ITEM_TYPE) { type = NavType.EnumType(ItemType::class.java) }
            )) {
            val id = it.arguments?.getInt(NavArgs.ITEM_ID)
            val type = it.arguments?.getParcelable(NavArgs.ITEM_TYPE, ItemType::class.java)
            DetailsScreen(
                navController = navController,
                itemId = id,
                itemType = type ?: ItemType.MOVIE,
            )
        }

        composable(
            route = "${AppScreen.Edit.route}/{${NavArgs.ITEM_ID}}/{${NavArgs.ITEM_TYPE}}",
            arguments = listOf(
                navArgument(NavArgs.ITEM_ID) { type = NavType.IntType },
                navArgument(NavArgs.ITEM_TYPE) { type = NavType.EnumType(ItemType::class.java) }
            )
        ) {
            val id = it.arguments?.getInt(NavArgs.ITEM_ID)
            val type = it.arguments?.getParcelable(NavArgs.ITEM_TYPE, ItemType::class.java)
            EditScreen(
                navController = navController,
                itemId = id,
                itemType = type ?: ItemType.MOVIE,
            )
        }
    }
}

fun NavHostController.navToItemDetails(id: Int, itemType: ItemType) {
    navigate("${AppScreen.Edit.route}/$id/$itemType")
}

fun NavHostController.navToItemEdit(id: Int?, itemType: ItemType) {
    navigate("${AppScreen.Edit.route}/$id/$itemType")
}