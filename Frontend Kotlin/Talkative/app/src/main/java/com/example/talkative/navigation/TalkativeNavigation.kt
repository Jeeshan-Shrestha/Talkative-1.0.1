package com.example.talkative.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.talkative.model.OwnProfileResponse.Message
import com.example.talkative.model.customDataPassing.ProfileArgument
import com.example.talkative.screens.CreatePost.CreatePostViewmodel
import com.example.talkative.screens.EditProfileScreen.EditProfileScreen
import com.example.talkative.screens.EditProfileScreen.EditProfileViewModel
import com.example.talkative.screens.HomeScreen.HomeScreen
import com.example.talkative.screens.LoginScreen.LoginScreen
import com.example.talkative.screens.LoginScreen.LoginViewModel
import com.example.talkative.screens.ProfileScreen.OtherUserProfileViewModel
import com.example.talkative.screens.ProfileScreen.OtherUserProflieScreen
import com.example.talkative.screens.ProfileScreen.OwnProfilePostViewmodel
import com.example.talkative.screens.ProfileScreen.ProfileScreen
import com.example.talkative.screens.SignupScreen.SignUpViewmodel
import com.example.talkative.screens.SignupScreen.SignupScreen
import com.example.talkative.screens.searchScreen.FollowUnFollowViewModel
import com.example.talkative.screens.searchScreen.SearchScreen
import com.example.talkative.screens.searchScreen.SearchViewModel
import com.example.talkative.utils.Constants
import kotlinx.serialization.json.Json


@Composable
fun TalkativeNavigation(){
    val navController = rememberNavController()

    val LoginViewModel = hiltViewModel<LoginViewModel>()
    val domain = "talkative-1-0-1-2.onrender.com"

    //Checking Login Status
    LaunchedEffect (Unit) {
        LoginViewModel.checkStatusLoginStatus(domain)
        if (LoginViewModel.isLoggined) {
            navController.navigate("main"){
                popUpTo("auth"){ inclusive= true} //clearing auth graph
                launchSingleTop=true
            }
        }
    }

    //Root navhost
    NavHost(navController = navController, startDestination = "auth"){
        //authgraph
        navigation(startDestination = TalkativeScreen.LoginScreen.name,route="auth"){

            composable(TalkativeScreen.LoginScreen.name) {
                LoginScreen(navController = navController, LoginViewModel = LoginViewModel)
            }
            composable(TalkativeScreen.SignUpScreen.name) {
                val signUpViewmodel: SignUpViewmodel = hiltViewModel()
                SignupScreen(navController = navController, SignupViewmodel = signUpViewmodel)
            }
        }

        //MainGraph
        // Main graph
        navigation(startDestination = TalkativeScreen.HomeScreen.name, route = "main") {

            //HOme screen
            composable(TalkativeScreen.HomeScreen.name) {

                val createPostViewmodel=hiltViewModel<CreatePostViewmodel>()

                HomeScreen(navController = navController,createPostViewmodel=createPostViewmodel)
            }
            //Search Screen
            composable(TalkativeScreen.SearchScreen.name) {
                val searchViewModel = hiltViewModel<SearchViewModel>()

                val followUnFollowViewmodel = hiltViewModel<FollowUnFollowViewModel>()

                val createPostViewmodel=hiltViewModel<CreatePostViewmodel>()
                SearchScreen(
                    navController = navController,
                    SearchViewModel = searchViewModel,
                    FollowUnFollowViewmodel = followUnFollowViewmodel,
                    createPostViewmodel=createPostViewmodel
                )
            }

            //ProfileScreen
            composable(TalkativeScreen.ProfileScreen.name) {
                val createPostViewmodel=hiltViewModel<CreatePostViewmodel>()

                val  OwnProfilePostViewmodel=hiltViewModel<OwnProfilePostViewmodel>()

                ProfileScreen(navController = navController,
                    createPostViewmodel = createPostViewmodel,
                    ownProfilePostViewmodel = OwnProfilePostViewmodel)
            }

            //EditProfileScreen
            composable(TalkativeScreen.EditProfileScreen.name){

                val editProfileViewModel = hiltViewModel<EditProfileViewModel>()

                    EditProfileScreen(navController=navController,editProfileViewModel=editProfileViewModel)
            }


            //otherUserProfileScreen
            composable(TalkativeScreen.OtherUserProfileScreen.name+"/{username}",
                arguments =listOf(navArgument(name="username"){type= NavType.StringType})){backStackEntry->
                val userName = backStackEntry.arguments?.getString("username")
                val createPostViewmodel=hiltViewModel<CreatePostViewmodel>()

                val OtherUserProfileViewModel = hiltViewModel<OtherUserProfileViewModel>()
                OtherUserProflieScreen(navController = navController,createPostViewmodel=createPostViewmodel,otherUsername=userName,
                    OtherUserProfileViewModel= OtherUserProfileViewModel)
            }
        }
    }
}