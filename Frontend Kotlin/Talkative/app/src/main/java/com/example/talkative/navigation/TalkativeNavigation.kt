package com.example.talkative.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.talkative.screens.HomeScreen.HomeScreen
import com.example.talkative.screens.LoginScreen.LoginScreen
import com.example.talkative.screens.LoginScreen.LoginViewModel
import com.example.talkative.screens.ProfileScreen.ProfileScreen
import com.example.talkative.screens.SignupScreen.SignUpViewmodel
import com.example.talkative.screens.SignupScreen.SignupScreen
import com.example.talkative.screens.searchScreen.SearchScreen
import com.example.talkative.utils.Constants


@Composable
fun TalkativeNavigation(){
    val navController = rememberNavController()

    val LoginViewModel = hiltViewModel<LoginViewModel>()
    val domain = "talkative-1-0-1-2.onrender.com"

    LaunchedEffect (Unit) {
        LoginViewModel.checkStatusLoginStatus(domain)
        if (LoginViewModel.isLoggined) {
            navController.navigate(TalkativeScreen.HomeScreen.name)
        }
    }

    //creating navhost
    NavHost(navController=navController, startDestination = TalkativeScreen.LoginScreen.name){
        //login screen
        composable(TalkativeScreen.LoginScreen.name){
            LoginScreen(navController=navController,LoginViewModel =LoginViewModel)
        }
        //signup screen
        composable(TalkativeScreen.SignUpScreen.name){

            val signUpViewmodel: SignUpViewmodel= hiltViewModel()

            SignupScreen(navController=navController, SignupViewmodel = signUpViewmodel)
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
            ProfileScreen(navController=navController)
        }

    }
}