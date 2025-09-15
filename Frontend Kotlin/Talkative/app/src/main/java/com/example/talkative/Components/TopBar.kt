package com.example.talkative.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.talkative.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TopBar(){
    CenterAlignedTopAppBar(
        title = {
            Row(modifier = Modifier.padding(10.dp)){

            }
        },
        navigationIcon = {
            Icon(painter = painterResource(R.drawable.talk),
                contentDescription = "Talkative")
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(painter = painterResource(R.drawable.messenger),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "messenger")
            }
        }
    )
}