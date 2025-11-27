package com.example.talkative.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TopBar(onChatClick:()-> Unit){
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
            IconButton(onClick = {
                //navigate to select People to chat with screen
                onChatClick.invoke()
            }) {
                Icon(painter = painterResource(R.drawable.messenger),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "messenger")
            }
        }
    )
}



//top bar with back button
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBack(text: String="xyz",onClick:()-> Unit){
    CenterAlignedTopAppBar(
        title = {
            Row(modifier = Modifier.padding(10.dp)){
                Text(text = text,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                onClick.invoke()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button")
            }
        }
    )
}

//top bar for chat app
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarForChatScreen(
    userName: String?,
    displayName:String?,
    avatar:String?,
    onClick: () -> Unit={}){
    TopAppBar(
        title ={
            Row(modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically){

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatar?:"https://i.imgur.com/veVP6GL.png")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "Comment Avatar",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    //DisplayName
                    Text(
                        text=displayName?:"unknown",
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium)
                    //username
                    Text(
                        text = userName?:"unknown",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        },
        navigationIcon = {
            IconButton(onClick = {
                onClick.invoke()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button")
            }
        }
    )
}