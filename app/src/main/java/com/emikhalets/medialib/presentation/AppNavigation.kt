package com.emikhalets.medialib.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.screens.main.MainScreen
import com.emikhalets.medialib.presentation.screens.saved_movie.SavedMovieScreen
import com.emikhalets.medialib.presentation.screens.search.SearchScreen
import com.emikhalets.medialib.presentation.screens.searched_movie.SearchedMovieScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Main.route) {

        composable(AppScreen.Main.route) {
            MainScreen(navController)
        }

        composable(AppScreen.Search.route) {
            SearchScreen(navController)
        }

        composable(
            "${AppScreen.SearchedMovie.route}/{${Args.MOVIE_ID.arg}}",
            arguments = listOf(navArgument(Args.MOVIE_ID.arg) { type = NavType.IntType })
        ) {
            SearchedMovieScreen(
                navController = navController,
                movieId = it.arguments?.getInt(Args.MOVIE_ID.arg) ?: -1
            )
        }

        composable(
            "${AppScreen.SearchedMovie.route}/{${Args.MOVIE_ID.arg}}",
            arguments = listOf(navArgument(Args.MOVIE_ID.arg) { type = NavType.IntType })
        ) {
            SearchedMovieScreen(
                navController = navController,
                movieId = it.arguments?.getInt(Args.MOVIE_ID.arg) ?: -1
            )
        }

        composable(
            "${AppScreen.SavedMovie.route}/{${Args.MOVIE_ID.arg}}",
            arguments = listOf(navArgument(Args.MOVIE_ID.arg) { type = NavType.IntType })
        ) {
            SavedMovieScreen(
                navController = navController,
                movieId = it.arguments?.getInt(Args.MOVIE_ID.arg) ?: -1
            )
        }

    }
}

fun NavHostController.navToSearchedMovie(id: Int) {
    navigate("${AppScreen.SearchedMovie.route}/$id")
}

fun NavHostController.navToSavedMovie(id: Int) {
    navigate("${AppScreen.SavedMovie.route}/$id")
}

sealed class AppScreen(val route: String, @StringRes val name: Int) {

    object Main : AppScreen("main", R.string.screen_name_main)
    object Search : AppScreen("search", R.string.screen_name_search)
    object SearchedMovie : AppScreen("searched_movie", R.string.screen_name_search)
    object SavedMovie : AppScreen("saved_movie", R.string.screen_name_saved_movie)

    companion object {
        fun getDrawerScreens(): List<AppScreen> {
            return listOf(Main, Search)
        }
    }
}

enum class Args(val arg: String) {
    MOVIE_ID("movie_id");
}