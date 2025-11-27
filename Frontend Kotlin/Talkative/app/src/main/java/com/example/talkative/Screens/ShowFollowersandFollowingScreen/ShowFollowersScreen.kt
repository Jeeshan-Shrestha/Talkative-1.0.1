package com.example.talkative.screens.ShowFollowersandFollowingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.components.EmptyText
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBarWithBack
import com.example.talkative.components.UserCompactCard
import com.example.talkative.components.sansButton
import com.example.talkative.navigation.TalkativeScreen
import com.example.talkative.screens.searchScreen.FollowUnFollowViewModel
import com.example.talkative.utils.LoadingState


@Composable
fun ShowFollowersScreen(navController: NavController,
                        FollowUnFollowViewModel: FollowUnFollowViewModel,
                        GetFollowersViewModel: GetFollowersViewModel,
                        username: String?) {

    val data = GetFollowersViewModel.item.message

    val uiState = GetFollowersViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        username?.let {
            GetFollowersViewModel.getFollowers(it)
        }
    }


    Scaffold(topBar = {
        TopBarWithBack(text = "Followers") {
            //onclick take user to previous screen
            navController.popBackStack()
        }

    }) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {


            //Loading animation
            if (uiState.value == LoadingState.LOADING) {
                SimpleLoadingAnimation(uiState = uiState)
            } else {
                //if there are no followers
                if (data.isEmpty()) {
                    EmptyText(text = "There are no Followers")
                }


                //followers list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //useUserCompactCard
                    items(data) { it ->
                        UserCompactCard(userInfo = it, followUnFollow = {
                            //follow or unfollow user
                            FollowUnFollowViewModel.FollowUnfollowuser(it)
                        }) { username,displayName,avatar ->
                            //open other userprofile
                            navController.navigate(TalkativeScreen.OtherUserProfileScreen.name + "/${username}")
                        }
                    }

                }

            }
        }
    }
}

