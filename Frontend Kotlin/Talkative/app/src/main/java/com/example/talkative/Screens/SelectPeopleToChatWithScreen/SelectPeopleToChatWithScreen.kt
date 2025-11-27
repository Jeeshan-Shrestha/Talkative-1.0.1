package com.example.talkative.screens.SelectPeopleToChatWithScreen

import android.net.Uri
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
import com.example.talkative.DataBase.UserViewModel
import com.example.talkative.components.EmptyText
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBarWithBack
import com.example.talkative.components.UserCompactCard
import com.example.talkative.navigation.TalkativeScreen
import com.example.talkative.screens.ShowFollowersandFollowingScreen.GetFollowingViewModel
import com.example.talkative.utils.LoadingState

@Composable
fun SelectPeopleToChatWithScreen(navController: NavController,
                                 UserViewModel: UserViewModel,
                                 GetFollowingViewModel: GetFollowingViewModel) {

    val data = GetFollowingViewModel.item.message

    val uiState = GetFollowingViewModel.state.collectAsState()

    //Collect from DataBase
    val userInfo = UserViewModel.userDetails.collectAsState()

    LaunchedEffect(userInfo.value?.username) {
        val username = userInfo.value?.username ?: return@LaunchedEffect

        if (GetFollowingViewModel.item.message.isEmpty()) {
            GetFollowingViewModel.getFollowing(username)
        }
    }

    Scaffold(topBar = {
        TopBarWithBack(text = "Let's Talk with Someone") {
            //onclick take user to previous screen
            navController.popBackStack()
        }
    }) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            //simple Loading animation
            if (uiState.value == LoadingState.LOADING) {
                SimpleLoadingAnimation(uiState = uiState)
            } else {
                if (data.isEmpty()) {
                    EmptyText(text = "Please Follow Someone To SendMessages")
                }

                //followers list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //useUserCompactCard
                    items(data) {
                        UserCompactCard(userInfo = it,
                            chatScreen = true){ username,displayName,avatar ->
                            //navigate to chatScreen and pass the username whom to talk
                            //spaces and / crashes app so
                            val encodedUsername = Uri.encode(username)
                            val encodedDisplayName = Uri.encode(displayName)
                            val encodedAvatar = Uri.encode(avatar?:"https://i.imgur.com/veVP6GL.png")
                            navController.navigate(TalkativeScreen.ChatScreen.name+"/${encodedUsername}/${encodedDisplayName}/${encodedAvatar}")
                        }
                    }
                }

            }
        }
    }
}
