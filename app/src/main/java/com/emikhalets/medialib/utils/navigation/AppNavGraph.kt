package com.emikhalets.medialib.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.medialib.presentation.screens.main.MainMenuScreen
import com.emikhalets.medialib.presentation.screens.movies.MoviesScreen
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.enums.SearchType

object NavArgs {
    const val ITEM_ID = "nav_arg_item_id"
    const val ITEM_TYPE = "nav_arg_item_type"
    const val SEARCH_TYPE = "nav_arg_search_type"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.MainMenu.route) {

        composable(AppScreen.MainMenu.route) {
            MainMenuScreen(
                navigateToMovies = { navController.navigate(AppScreen.Movies.route) },
                navigateToSerials = { navController.navigate(AppScreen.Serials.route) }
            )
        }

        composable(AppScreen.Movies.route) {
            MoviesScreen(
                navigateToMovieDetails = { movieId -> },
                navigateToMovieEdit = { }
            )
        }

        composable(AppScreen.Serials.route) {
        }

//        composable(AppScreen.Search.route) {
//            SearchMainScreen(navController)
//        }
//
//        composable(
//            route = AppScreen.SearchWebView.route,
//            arguments = listOf(navArgument(NavArgs.SEARCH_TYPE) { type = NavType.StringType })) {
//            val type = it.arguments?.getString(NavArgs.SEARCH_TYPE)
//            SearchWebviewScreen(
//                navController = navController,
//                type = SearchType.valueOf(type ?: SearchType.MOVIE.toString())
//            )
//        }
//
//        composable(
//            route = AppScreen.Details.route,
//            arguments = listOf(
//                navArgument(NavArgs.ITEM_ID) { type = NavType.IntType },
//                navArgument(NavArgs.ITEM_TYPE) { type = NavType.StringType }
//            )) {
//            val id = it.arguments?.getInt(NavArgs.ITEM_ID)
//            val type = it.arguments?.getString(NavArgs.ITEM_TYPE)
//            DetailsScreen(
//                navController = navController,
//                itemId = id,
//                itemType = ItemType.valueOf(type ?: ItemType.MOVIE.toString())
//            )
//        }
//
//        composable(
//            route = AppScreen.Edit.route,
//            arguments = listOf(
//                navArgument(NavArgs.ITEM_ID) { type = NavType.IntType },
//                navArgument(NavArgs.ITEM_TYPE) { type = NavType.StringType }
//            )
//        ) {
//            val id = it.arguments?.getInt(NavArgs.ITEM_ID)
//            val type = it.arguments?.getString(NavArgs.ITEM_TYPE)
//            EditScreen(
//                navController = navController,
//                itemId = id ?: 0,
//                itemType = ItemType.valueOf(type ?: ItemType.MOVIE.toString())
//            )
//        }
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