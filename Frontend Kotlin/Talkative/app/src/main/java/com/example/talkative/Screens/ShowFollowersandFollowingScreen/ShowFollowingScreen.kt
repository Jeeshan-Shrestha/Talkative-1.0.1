package com.example.talkative.screens.ShowFollowersandFollowingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.talkative.components.EmptyText
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBarWithBack
import com.example.talkative.components.UserCompactCard
import com.example.talkative.navigation.TalkativeScreen
import com.example.talkative.screens.searchScreen.FollowUnFollowViewModel

@Composable
fun ShowFollowingScreen(navController: NavController,
                        FollowUnFollowViewModel: FollowUnFollowViewModel,
                        GetFollowingViewModel: GetFollowingViewModel,
                        username: String?){

    val data =  GetFollowingViewModel.item.message

    val uiState = GetFollowingViewModel.state.collectAsState()

    LaunchedEffect(Unit){
        username?.let {
            GetFollowingViewModel.getFollowing(it)
        }
    }

    Scaffold(topBar = {
        TopBarWithBack(text = "Following"){
            //onclick take user to previous screen
            navController.popBackStack()
        }
    }) {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            //simple Loading animation
            SimpleLoadingAnimation(uiState = uiState)

            if(data.isEmpty()){
                EmptyText(text="Not Following anyone")
            }

            //followers list
            LazyColumn(modifier = Modifier
                .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)){
                //useUserCompactCard
                items(data){
                    UserCompactCard(userInfo = it, followUnFollow = {
                        //follow or unfollow user
                        FollowUnFollowViewModel.FollowUnfollowuser(it)
                    }){username->
                        //open other user profile
                        //open other userprofile
                        navController.navigate(TalkativeScreen.OtherUserProfileScreen.name+"/${username}")
                    }
                }
            }

        }
    }
}
