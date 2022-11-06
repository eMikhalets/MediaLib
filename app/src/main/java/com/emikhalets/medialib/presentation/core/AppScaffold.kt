package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.AppScreen
import com.emikhalets.medialib.presentation.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val rootScreens = remember {
        listOf(AppScreen.Movies, AppScreen.Serials, AppScreen.Books, AppScreen.Music)
    }

    val title = AppScreen.getTitle(navBackStackEntry)
    val currentScreen = AppScreen.getScreen(navBackStackEntry)
    val isCurrentScreenRoot = rootScreens.contains(currentScreen)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                title,
                isCurrentScreenRoot,
                scaffoldState.drawerState,
                scope,
                navController
            )
        },
        drawerContent = {
            AppDrawer(
                isCurrentScreenRoot,
                scaffoldState.drawerState,
                scope,
                navController,
                navBackStackEntry,
                rootScreens
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
private fun AppToolbar(
    title: String,
    isCurrentScreenRoot: Boolean,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController,
) {
    TopAppBar(
        title = { Text(title) },
        elevation = 0.dp,
        navigationIcon = {
            if (isCurrentScreenRoot) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { scope.launch { drawerState.open() } }
                        .padding(20.dp, 16.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .padding(20.dp, 16.dp)
                )
            }
        }
    )
}

@Composable
private fun AppDrawer(
    isCurrentScreenRoot: Boolean,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    rootScreens: List<AppScreen>,
) {
    if (isCurrentScreenRoot) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp)
        )
        Divider()
        rootScreens.forEach { screen ->
            Text(
                text = stringResource(screen.titleRes),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (navBackStackEntry?.destination?.route == screen.route) {
                            MaterialTheme.colors.primary.copy(alpha = 0.2f)
                        } else {
                            Color.Transparent
                        }
                    )
                    .clickable {
                        if (navBackStackEntry?.destination?.route != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        scope.launch { drawerState.close() }
                    }
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScaffoldPreview() {
    AppTheme {
        AppScaffold(rememberNavController()) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToolbarPreview() {
    AppTheme {
        AppToolbar(
            "Test title",
            false,
            rememberScaffoldState().drawerState,
            rememberCoroutineScope(),
            rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun DrawerPreview() {
    AppTheme {
        Column(Modifier.fillMaxSize()) {
            AppDrawer(
                true,
                rememberScaffoldState().drawerState,
                rememberCoroutineScope(),
                rememberNavController(),
                rememberNavController().currentBackStackEntryAsState().value,
                listOf(AppScreen.Movies, AppScreen.Serials, AppScreen.Books, AppScreen.Music),
            )
        }
    }
}