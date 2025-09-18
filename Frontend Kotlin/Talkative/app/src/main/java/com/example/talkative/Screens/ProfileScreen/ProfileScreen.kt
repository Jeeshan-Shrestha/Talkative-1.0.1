package com.example.talkative.screens.ProfileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.talkative.R
import com.example.talkative.components.BottomBar
import com.example.talkative.components.CreatePostDialouge
import com.example.talkative.components.PostCard
import com.example.talkative.components.sansButton
import com.example.talkative.model.MockData
import com.example.talkative.model.Post
import com.example.talkative.model.User



@Composable
fun ProfileScreen(
    username:String = "you",
    navController: NavController= NavController(LocalContext.current)
){

    val isOwnProfile = username == "you"

    val profileData: User = if(isOwnProfile){
        MockData.mockUser
    }else{
            //else get the user from backend
            //send id to backend and it will send
        MockData.mockUser
    }

    //boolean value for ture or false
    var isFollowing by remember {
        mutableStateOf(profileData.isFollowing)
    }

    var FollowingCount by remember {
        mutableStateOf(profileData.following)
    }

    var followersCount by remember {
        mutableStateOf(profileData.followers)
    }

    //handle follow
    val handlefollow = {
        isFollowing = !isFollowing
        followersCount= if (isFollowing) followersCount +1 else followersCount-1

    }

    //to add posts by clicsking + icon from profile screen
    val showPostDialouge = remember { mutableStateOf(false) }

    // Mock user posts
    val userPosts = listOf(
        Post(
            id = "user-1",
            user = profileData,
            content = "Just wrapped up an amazing design workshop with some incredibly talented folks! There's nothing quite like collaborating with passionate creatives. Thanks to everyone who participated! 🎨✨",
            image = "https://images.unsplash.com/photo-1623715537851-8bc15aa8c145",
            likes = 156,
            comments = 23,
            shares = 8,
            timestamp = "1d",
            isLiked = true
        ),
        Post(
            id = "user-2",
            user = profileData,
            content = "Saturday morning vibes ☕ Starting the weekend with a perfect cup of coffee and some light reading. What are your weekend plans?",
            image = "https://images.unsplash.com/photo-1532980400857-e8d9d275d858",
            likes = 89,
            comments = 12,
            shares = 3,
            timestamp = "3d"
        )
    )

    Scaffold(bottomBar = {
        BottomBar(navController=navController){
            showPostDialouge.value=true
        }
    }) {it->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            LazyColumn(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            //    contentPadding = PaddingValues(10.dp)
            ) {

                //Cover Image
                item {
                    Box(modifier = Modifier
                        .height(230.dp)
                        .fillMaxWidth()){

                        AsyncImage(
                            model =profileData.coverImage ?: "https://images.unsplash.com/photo-1501785888041-af3ef285b470" ,
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = "Cover Image",
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        AsyncImage(
                            model =profileData.avatar ?: "https://images.unsplash.com/photo-1501785888041-af3ef285b470" ,
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .offset(x = 20.dp,y=(150).dp)
                                .size(128.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        // Overlay
                        Box(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                            contentAlignment = Alignment.TopEnd){

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                if(isOwnProfile){
                                    FilledTonalButton(onClick = {
                                        //edit profile
                                    },
                                        colors = ButtonDefaults.buttonColors(Color.White)){
                                        Icon(imageVector = Icons.Default.Edit,
                                            tint = Color.Black,
                                            modifier = Modifier.size(16.dp),
                                            contentDescription = "edit profile")

                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Edit Profile",
                                            style = TextStyle(color = Color.Black))
                                    }

//                                    IconButton(onClick = {}) {
//                                        Icon(imageVector = Icons.Default.Settings,
//                                            contentDescription = "Settings")
//                                    }
                                    FilledTonalButton(onClick = {
                                        //edit profile
                                    },
                                        colors = ButtonDefaults.buttonColors(Color.White)) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            tint = Color.Black,
                                            modifier = Modifier.size(16.dp),
                                            contentDescription = "edit profile"
                                        )
                                    }


                                }else{
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.HorizontalRule,
                                            contentDescription = "More"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                //Profile Info
                item{
                    Column(modifier = Modifier
                        .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){

                        //Avatar (ocverlapping cover image)
//                        Row(modifier = Modifier,
//                            verticalAlignment = Alignment.Bottom,
//                            horizontalArrangement = Arrangement.SpaceBetween){
//
//                            AsyncImage(
//                                model =profileData.avatar ?: "https://images.unsplash.com/photo-1501785888041-af3ef285b470" ,
//                                placeholder = painterResource(R.drawable.placeholder),
//                                contentDescription = "Profile picture",
//                                modifier = Modifier
//                                    .offset(y=(-100).dp)
//                                    .size(128.dp)
//                                    .clip(CircleShape),
//                                contentScale = ContentScale.Crop
//                            )
//                        }

                        //Spacer
                        Spacer(modifier = Modifier.height(25.dp))

                        //display name and username
                        Column(modifier = Modifier
                           // .offset(y=(-100).dp)
                            .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),) {

                            //follow and following button
                            if(!isOwnProfile){
                                //button
                                sansButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    textcolor = if(isFollowing) Color.Black else Color.White,
                                    text =if (isFollowing) "Following" else "Follow",
                                    icon = false,
                                    color =  if (isFollowing)
                                        Color.White
                                    else
                                        Color.Black
                                ){
                                    handlefollow.invoke()
                                }
                            }

                            //Name and username of the people
                            Text(text = profileData.name ?:"unknown",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold)

                            Text(text = profileData.username?:"username",
                                style=MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)

                            //Bio
                            profileData.bio?.let { it->
                                Text(text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight)
                            }

                            //Meta info website , joined date etc
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){

                                //website link
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)){

                                   profileData.website?.let {it->
                                       Icon(imageVector = Icons.Default.Link,
                                           contentDescription = "Website",
                                           modifier = Modifier.size(18.dp),
                                           tint = MaterialTheme.colorScheme.primary)

                                       Text(text = it?: "No link",
                                           style = MaterialTheme.typography.bodyMedium,
                                           color=MaterialTheme.colorScheme.primary)
                                   }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)){

                                    profileData.joinDate?.let {it->
                                        Icon(imageVector = Icons.Default.CalendarToday,
                                            contentDescription = "Website",
                                            modifier = Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant)

                                        Text(text = it?: "No link",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color=MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }

                            Row(modifier = Modifier.offset(y=25.dp),
                                horizontalArrangement = Arrangement.spacedBy(24.dp)){

                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                                    Text(text = profileData.posts.toString(),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold)

                                    Text(text = "Posts",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                                    Text(text = profileData.followers.toString(),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold)

                                    Text(text = "Followers",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                                    Text(text = profileData.following.toString(),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold)

                                    Text(text = "Following",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                        }
                    }
                }

                //Content Tabs Posts
              items(userPosts){posts->
                  PostCard(post = posts,
                      modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 8.dp),
                    ownProfile =true)
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