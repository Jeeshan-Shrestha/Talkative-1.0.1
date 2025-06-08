package com.example.talkative.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.talkative.Screens.addUsername.AddUserNameScreen
import com.example.talkative.Screens.addUsername.LoginViewModel
import com.example.talkative.Screens.homeScreen.HomeScreen
import com.example.talkative.Screens.homeScreen.HomeScreenViewModel
import com.example.talkative.Screens.signupScreen.SignUpScreen

@Composable
fun TalkativeNavigation(){
    val navController= rememberNavController()

    NavHost(navController = navController,
        startDestination = TalkativeScreen.AdduserName.name) {

        //we are passing usersname from addusername screen to home screeen so we have to recieve it here
        val route1= TalkativeScreen.HomeScreen.name
        composable("$route1/{username}",
            arguments = listOf(
            navArgument(name = "username"){ type= NavType.StringType })){BackStackEntry-> //variable that contains information we want
            val viewmodel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()

            val name = BackStackEntry.arguments?.getString("username")

            HomeScreen(viewModel = viewmodel, username=name)
        }

        //Login Screen
        composable(TalkativeScreen.AdduserName.name) {
            val loginViewModel:LoginViewModel = hiltViewModel<LoginViewModel>()
            AddUserNameScreen(navController=navController,
                loginViewModel = loginViewModel)
        }

        //SignUp Screen
        composable(TalkativeScreen.signUpUser.name){
            SignUpScreen(NavController =navController)
        }

    }
}