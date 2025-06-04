package com.example.talkative.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.talkative.Screens.homeScreen.HomeScreen
import com.example.talkative.Screens.homeScreen.HomeScreenViewModel

@Composable
fun TalkativeNavigation(){
    val navController= rememberNavController()

    NavHost(navController = navController,
        startDestination = TalkativeScreen.HomeScreen.name) {

        composable(TalkativeScreen.HomeScreen.name){
            val viewmodel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(viewmodel)
        }

    }
}