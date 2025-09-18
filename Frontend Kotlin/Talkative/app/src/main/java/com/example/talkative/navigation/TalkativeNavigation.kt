package com.example.talkative.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.talkative.Talkative
import com.example.talkative.screens.HomeScreen.HomeScreen
import com.example.talkative.screens.LoginScreen.LoginScreen
import com.example.talkative.screens.SignupScreen.SignupScreen
import com.example.talkative.screens.searchScreen.SearchScreen

@Composable
fun TalkativeNavigation(){
    val navController = rememberNavController()

    //creating navhost
    NavHost(navController=navController, startDestination = TalkativeScreen.LoginScreen.name){
        //login screen
        composable(TalkativeScreen.LoginScreen.name){
            LoginScreen(navController=navController)
        }
        //signup screen
        composable(TalkativeScreen.SignUpScreen.name){
            SignupScreen(navController=navController)
        }
        //home screen
        composable(route = TalkativeScreen.HomeScreen.name) {
            HomeScreen(navController=navController)
        }
        //searchScreen
        composable(route= TalkativeScreen.SearchScreen.name) {
            SearchScreen(navController=navController)
        }

        //ProfileScreen
        composable(route= TalkativeScreen.ProfileScreen.name) {

        }

    }
}