package com.example.talkative.screens.ShowFollowersandFollowingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.talkative.components.TopBarWithBack

@Composable
fun ShowFollowingScreen(){


    Scaffold(topBar = {
        TopBarWithBack(text = "Following"){
            //onclick take user to previous screen
        }
    }) {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            //followers list
            LazyColumn(modifier = Modifier
                .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)){
                //useUserCompactCard
            }

        }
    }
}
