package com.example.talkative.components

import android.graphics.fonts.Font
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.model.MockData
import com.example.talkative.model.User


@Composable
fun UserCard(
    user: User= MockData.mockUsers[0]){


    val isFollowing = remember {
        mutableStateOf(user.isFollowing)
    }

    val followersCount = remember {
        mutableStateOf(user.followers)
    }

    val handlefollow = {
        isFollowing.value = !isFollowing.value
        followersCount.value= if (isFollowing.value) followersCount.value +1 else followersCount.value-1

    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable{
            //we will navigate to user Profile when Clicked
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border =BorderStroke(2.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)){

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "userimage",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(49.dp)
                    .clip(CircleShape)
            )

            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(15.dp))

            user.bio?.let { it->
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                )
            }

            Row(modifier = Modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp)){

                //For Posts
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = user.posts.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Posts",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

                //For followers

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = user.followers.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Followers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }


                //For Following

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = followersCount.value.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Following",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }
            }

            //button
            sansButton(
                modifier = Modifier.fillMaxWidth(),
                textcolor = if(isFollowing.value) Color.Black else Color.White,
                text =if (isFollowing.value) "Following" else "Follow",
                icon = false,
                color =  if (isFollowing.value)
                    Color.White
                else
                    Color.Black
            ){
                handlefollow.invoke()
            }


        }

    }

}