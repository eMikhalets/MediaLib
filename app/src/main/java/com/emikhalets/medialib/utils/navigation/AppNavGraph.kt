package com.emikhalets.medialib.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.presentation.screens.library.LibraryScreen
import com.emikhalets.medialib.presentation.screens.movies.details.MovieDetailsScreen
import com.emikhalets.medialib.presentation.screens.movies.edit.MovieEditScreen
import com.emikhalets.medialib.presentation.screens.movies.list.MoviesScreen
import com.emikhalets.medialib.presentation.screens.searching.SearchMainScreen
import com.emikhalets.medialib.presentation.screens.searching.SearchWithImdbScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Library.route) {

        composable(AppScreen.Library.route) {
            LibraryScreen(
                navigateToMovies = { navController.navigate(AppScreen.Movies.route) },
                navigateToSerials = {}
            )
        }

        composable(AppScreen.Movies.route) {
            MoviesScreen(
                navigateToMovieDetails = { movieId ->
                    navController.navigate("${AppScreen.MovieDetails.route}/$movieId")
                },
                navigateToMovieEdit = {
                    navController.navigate("${AppScreen.MovieEdit.route}/0")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${AppScreen.MovieDetails.route}/{${NavArgs.MOVIE_ID}}",
            arguments = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.LongType })
        ) {
            MovieDetailsScreen(
                navigateToMovieEdit = { movieId ->
                    navController.navigate("${AppScreen.MovieEdit.route}/$movieId")
                },
                navigateBack = { navController.popBackStack() },
                movieId = it.arguments?.getLong(NavArgs.MOVIE_ID) ?: 0,
            )
        }

        composable(
            route = "${AppScreen.MovieEdit.route}/{${NavArgs.MOVIE_ID}}",
            arguments = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.LongType })
        ) {
            MovieEditScreen(
                navigateBack = { navController.popBackStack() },
                movieId = it.arguments?.getLong(NavArgs.MOVIE_ID) ?: 0,
            )
        }

        composable(AppScreen.Searching.route) {
            SearchMainScreen(
                navigateImdbSearching = { navController.navigate(AppScreen.SearchImdb.route) }
            )
        }

        composable(AppScreen.SearchImdb.route) {
            SearchWithImdbScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}