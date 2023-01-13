package com.emikhalets.medialib.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.medialib.presentation.screens.library.LibraryScreen
import com.emikhalets.medialib.presentation.screens.movies.details.MovieDetailsScreen
import com.emikhalets.medialib.presentation.screens.movies.list.MoviesScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, AppScreen.Library.route) {

        composable(AppScreen.Library.route) {
            LibraryScreen(
                navigateToMovies = { navController.navigate(AppScreen.Movies.route) },
                navigateToSerials = { navController.navigate(AppScreen.Serials.route) }
            )
        }

        composable(AppScreen.Movies.route) {
            MoviesScreen(
                navigateToMovieDetails = { movieId ->
                    navController.navigate("${AppScreen.MovieDetails.route}/$movieId")
                },
                navigateToMovieEdit = {},
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${AppScreen.MovieDetails.route}/{${NavArgs.MOVIE_ID}}",
            arguments = listOf(navArgument(NavArgs.MOVIE_ID) { type = NavType.LongType })
        ) {
            MovieDetailsScreen(
                navigateToMovieEdit = { movieId -> },
                navigateBack = { navController.popBackStack() },
                movieId = it.arguments?.getLong(NavArgs.MOVIE_ID) ?: 0,
            )
        }

    }
}