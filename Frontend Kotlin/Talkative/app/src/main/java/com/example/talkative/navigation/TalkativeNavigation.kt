package com.example.talkative.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.talkative.Screens.SelectuserScreen.SelectuserScreen
import com.example.talkative.Screens.SelectuserScreen.SelectuserScreenViewmodel
import com.example.talkative.Screens.addUsername.AddUserNameScreen
import com.example.talkative.Screens.addUsername.LoginViewModel
import com.example.talkative.Screens.homeScreen.HomeScreen
import com.example.talkative.Screens.homeScreen.HomeScreenViewModel
import com.example.talkative.Screens.signupScreen.SignUpScreen
import com.example.talkative.Screens.signupScreen.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun TalkativeNavigation(){
    val navController= rememberNavController()

//TalkativeScreen.AdduserName.name
    NavHost(navController = navController,
        startDestination = TalkativeScreen.AdduserName.name) {


        //we are passing usersname from addusername screen to home screeen so we have to recieve it here
        val route1= TalkativeScreen.HomeScreen.name
        composable("$route1/{myname}/{recipientUsername}",
            arguments = listOf(
            navArgument(name = "myname"){ type= NavType.StringType},
                navArgument(name = "recipientUsername"){type=NavType.StringType}
            )){BackStackEntry-> //variable that contains information we want

            //websocket viewemodel
            val websocketviewmodel :HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()

            val myname = BackStackEntry.arguments?.getString("myname")
            val recipientUsername=BackStackEntry.arguments?.getString("recipientUsername")

            HomeScreen(viewModel = websocketviewmodel,
                myname=myname,
                recipientUsername=recipientUsername,
                navController=navController)
        }

        //Login Screen
        composable(TalkativeScreen.AdduserName.name) {
            val loginViewModel:LoginViewModel = hiltViewModel<LoginViewModel>()
            AddUserNameScreen(navController=navController,
                loginViewModel = loginViewModel)
        }

        //SignUp Screen
        composable(TalkativeScreen.signUpUser.name){
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(NavController =navController, signUpViewModel = signUpViewModel)
        }

        //select user screen
        val route2=TalkativeScreen.SelectuserScreen.name
        composable("$route2/{name}",
            arguments = listOf(
                navArgument(name="name"){type=NavType.StringType})){BackStackEntry->

            val name = BackStackEntry.arguments?.getString("name")

            val selectuserViewmodel:SelectuserScreenViewmodel = hiltViewModel<SelectuserScreenViewmodel>()
            SelectuserScreen(selectuser=selectuserViewmodel
                ,navController=navController,
                myname=name)
        }

    }
}