package com.example.talkative.screens.HomeScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.talkative.DataBase.UserInformation
import com.example.talkative.DataBase.UserViewModel
import com.example.talkative.components.BottomBar
import com.example.talkative.components.CreatePostDialouge
import com.example.talkative.components.EmptyText
import com.example.talkative.components.OnyourMind
import com.example.talkative.components.PostCard
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBar
import com.example.talkative.model.MockData
import com.example.talkative.navigation.TalkativeScreen
import com.example.talkative.screens.CreatePost.CreatePostViewmodel
import com.example.talkative.screens.ProfileScreen.LikeUnLikeViewModel
import com.example.talkative.utils.LoadingState
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController= NavController(LocalContext.current),
               createPostViewmodel: CreatePostViewmodel,
               LikeUnLikeViewModel: LikeUnLikeViewModel,
               UserViewModel: UserViewModel ,
               HomeFeedViewModel:HomeFeedViewModel,
               GetMySelfViewModel: GetMySelfViewModel) {

    val showPostDialouge = remember { mutableStateOf(false) }

    val data = HomeFeedViewModel.item.message

    val uiStateForHomeFeed = HomeFeedViewModel.state.collectAsState()

    val myself=GetMySelfViewModel.item.message

    //to make refresh like instagram
    //making pull to refresh like instagram
    val isRefreshing = remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            HomeFeedViewModel.Homefeed()
        }
    )

    //Stop Loading when ui is loaded
    LaunchedEffect(uiStateForHomeFeed.value) {
        isRefreshing.value = false
    }
    //Storing user info in dataBase
    LaunchedEffect(myself) {

        if(!myself.username.isNullOrEmpty()){
            Log.d("HomeScreen", "Saving user to DB: $myself")
            UserViewModel.addUserDetails(user = UserInformation(
                username = myself.username,
                displayName = myself.displayName,
                avatar = myself.avatar
            ))
        }

    }


    Scaffold(
        topBar = {
            TopBar(){
                navController.navigate(TalkativeScreen.SelectPeopleToChatWithScreen.name)
            }
        },
        bottomBar = {
            BottomBar(navController = navController) {
                showPostDialouge.value = true
            }
        }
    ) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            //wrapping inside box so that refresh will work
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .fillMaxSize()
            ) {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = (pullRefreshState.progress * 80f).roundToInt() // UI pull-down animation
                                )
                            }
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 17.dp)
                    ) {
                        item {
                            OnyourMind(createPostViewmodel=createPostViewmodel)
                            SimpleLoadingAnimation(uiState = uiStateForHomeFeed)
                        }
                        if(uiStateForHomeFeed.value != LoadingState.LOADING) {
                            if(data.isEmpty()){
                                item {
                                    Spacer(modifier = Modifier.height(200.dp))
                                    EmptyText("You are all Caughtup")
                                }
                            }else {
                                items(data) { it ->
//                    PostCard(post = posts){
//                        //when user clicks the username or name or profile navigate to otheruser screen
//                        navController.navigate(TalkativeScreen.OtherUserProfileScreen.name)
//                    }
                                    PostCard(
                                        post = it,
                                        ownProfile = false,
                                        LikeunLikeViewModel = LikeUnLikeViewModel,
                                        onUserclick = { username ->
                                            navController.navigate(TalkativeScreen.OtherUserProfileScreen.name + "/${username}")
                                        },
                                        onCommentClick = { commentId ->
                                            navController.navigate(TalkativeScreen.CommentScreen.name + "/${commentId}")
                                        })
                                }
                            }
                        }
                    }
                }

                    PullRefreshIndicator(
                        refreshing = isRefreshing.value,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )

                    if (showPostDialouge.value) {
                        CreatePostDialouge(
                            onDismiss = {
                                showPostDialouge.value = false
                            },
                            onPost = {
                                //we will handle post logic here
                                showPostDialouge.value = false
                            },
                            createPostViewmodel = createPostViewmodel
                        )
                    }

                }
            }
        }
    }