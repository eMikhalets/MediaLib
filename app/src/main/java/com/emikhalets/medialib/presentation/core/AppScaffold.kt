package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.medialib.utils.navigation.AppScreen
import com.emikhalets.medialib.utils.navigation.AppScreen.Companion.getBottomBarIconRes
import com.emikhalets.medialib.utils.navigation.AppScreen.Companion.getBottomBarTextRes

@Composable
fun AppScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { AppBottomBar(navController) },
        content = { Box(modifier = Modifier.padding(it)) { content() } }
    )
}

@Composable
private fun AppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        AppScreen.getBottomBarItems().forEach { screen ->
            BottomNavigationItem(
                icon = { IconPrimary(screen.getBottomBarIconRes()) },
                label = { Text(stringResource(screen.getBottomBarTextRes())) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // TODO: navigate to library screen?
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}