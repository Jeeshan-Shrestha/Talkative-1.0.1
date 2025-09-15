package com.example.talkative.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.talkative.navigation.TalkativeScreen

@Composable
fun BottomBar(navController: NavController) {
    val navigationItems = listOf(
        TalkativeScreen.HomeScreen,
        TalkativeScreen.SearchScreen,
        TalkativeScreen.PostScreen,
        TalkativeScreen.ProfileScreen
    )

    // Observe current route
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = Modifier.height(60.dp),
        tonalElevation = 10.dp
    ) {
        navigationItems.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination == screen.name,
                onClick = {
                    navController.navigate(screen.name) {
                        // Prevent building up back stack with multiple copies
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (screen) {
                        TalkativeScreen.HomeScreen -> Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(28.dp)
                        )
                        TalkativeScreen.SearchScreen -> Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(28.dp)
                        )
                        TalkativeScreen.PostScreen -> Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Post",
                            modifier = Modifier.size(28.dp)
                        )
                        TalkativeScreen.ProfileScreen -> Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(28.dp)
                        )
                        else -> {}
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black,
                    indicatorColor = Color.Black
                )
            )
        }
    }
}
