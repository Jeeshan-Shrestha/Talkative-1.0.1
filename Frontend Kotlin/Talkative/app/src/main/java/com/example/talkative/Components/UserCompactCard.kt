package com.example.talkative.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.model.GetFollowersResponse.Message


@Composable
fun UserCompactCard(userInfo: Message,
                    chatScreen: Boolean=false,
                    followUnFollow:(String)-> Unit={},
                    openUserProfile:(String,String,String?)-> Unit={_,_,_ ->}){

    val isFollowing= remember{
        mutableStateOf(userInfo.following)
    }

    val handleFollow ={
        isFollowing.value=!isFollowing.value
    }

    val isOwnProfile: Boolean = userInfo.ownProfile

    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable{
            //open userprofile on click
            if(!isOwnProfile) {
                openUserProfile(userInfo.username,userInfo.displayName,userInfo.avatar)
            }
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)){

        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)) {
                //user avatar
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userInfo.avatar?:"https://i.imgur.com/veVP6GL.png")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "userimage",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(49.dp)
                        .clip(CircleShape)
                )

                //username and  displayName
                Column {
                    Text(
                        text = userInfo.username?:"unknown",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = userInfo.displayName?:"unknown",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if(!isOwnProfile && !chatScreen) {
                sansButton(
                    modifier = Modifier,
                    textcolor = if (isFollowing.value) Color.Black else Color.White,
                    text = if (isFollowing.value) "Following" else "Follow",
                    icon = false,
                    color = if (isFollowing.value)
                        Color.White
                    else
                        Color.Black) {
                    //follow or unfollow should happen
                    handleFollow.invoke()
                    followUnFollow(userInfo.username)
                }
            }


        }


    }
}