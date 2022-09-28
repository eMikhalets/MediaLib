package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.presentation.AppScreen
import com.emikhalets.medialib.presentation.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppTopBar(navController, scaffoldState.drawerState) },
        drawerContent = { AppDrawer(navController, scaffoldState.drawerState) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun AppTopBar(navController: NavHostController, drawerState: DrawerState) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text("Some title") },
        elevation = 0.dp,
        navigationIcon = {
            if (navBackStackEntry?.destination?.route == AppScreen.Main.route) {
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
fun AppDrawer(navController: NavHostController, drawerState: DrawerState) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()

    val screens = remember {
        val list = mutableStateListOf<AppScreen>()
        list.addAll(AppScreen.getScreens())
        list
    }

    Text(
        text = "Some header",
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )

    Divider()

    screens.forEach { screen ->
        Text(
            text = stringResource(screen.name),
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
                        navController.navigate(screen.route)
                    }
                    scope.launch { drawerState.close() }
                }
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview() {
    AppTheme {
        AppTopBar(rememberNavController(), rememberScaffoldState().drawerState)
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDrawerPreview() {
    AppTheme {
        Column {
            AppDrawer(rememberNavController(), rememberScaffoldState().drawerState)
        }
    }
}