package com.example.talkative.screens.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.talkative.components.BottomBar
import com.example.talkative.components.CreatePostDialouge
import com.example.talkative.components.OnyourMind
import com.example.talkative.components.PostCard
import com.example.talkative.components.TopBar
import com.example.talkative.model.MockData


@Composable
fun HomeScreen(navController: NavController= NavController(LocalContext.current)){

    val showPostDialouge = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController=navController){
                showPostDialouge.value=true
            }
        }
    ) {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            LazyColumn(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 17.dp)){
                item {
                    OnyourMind()

                }
                items(MockData.mockPosts){posts->
                    PostCard(post = posts)
                }

            }

            if(showPostDialouge.value){
                CreatePostDialouge(onDismiss = {
                    showPostDialouge.value=false
                },
                    onPost = {
                        //we will handle post logic here
                        showPostDialouge.value=false
                    })
            }

        }
    }
}