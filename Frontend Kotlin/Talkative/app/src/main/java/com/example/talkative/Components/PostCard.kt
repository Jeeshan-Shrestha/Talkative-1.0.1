package com.example.talkative.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.model.OwnProfileResponse.Post
import com.example.talkative.screens.ProfileScreen.LikeUnLikeViewModel


@Composable
fun PostCard(
    modifier: Modifier= Modifier.padding(start = 8.dp, end = 8.dp),
    post: Post,
    LikeunLikeViewModel: LikeUnLikeViewModel= hiltViewModel(),
    ownProfile: Boolean=false,
    onCommentClick:(String)-> Unit,
    onDeletePost:(String)->Unit={},
    onUserclick: (String)->Unit ={}
){

    //state for isLiked
    var isLiked = remember {
        mutableStateOf(post.liked)
    }

    var likescount = remember {
        mutableStateOf(post.likes)
    }
    
    val handlelike = {
        isLiked.value=!isLiked.value
        likescount.value= if(isLiked.value) likescount.value + 1 else likescount.value -1
    }

    //to handle drop down to delete a post
    val expanded = remember {
        mutableStateOf(false)
    }

    Card(modifier = modifier.fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {

        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween){

            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable{ onUserclick.invoke(post.username?:"no username")},
                horizontalArrangement = Arrangement.Start,

                verticalAlignment = Alignment.CenterVertically){

                //profile Picture
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.avatar?:"https://i.imgur.com/veVP6GL.png")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "userimage",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                )

                Column(modifier = Modifier.padding(start = 20.dp)) {
                    Text(
                        text = post.displayName?:"No name",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "@${post.username} • ${post.postDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                //Delete Post option
                if(ownProfile){
               Row(modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.End) {
                   IconButton(onClick = { /* More options */
                       //delete post
                       expanded.value=true
                   }) {
                       Icon(
                           imageVector = Icons.Default.MoreHoriz,
                           contentDescription = "More options"
                       )
                   }
                   DropdownMenuWithDetails(text = "Delete Post", expanded = expanded.value, onDismiss = {
                       expanded.value=false
                   },
                       onClick = {
                           //make api request to delete the post and navigate to this screen only
                           onDeletePost(post.id)
                           expanded.value=false
                       })

               } }
            }



            // Post content
            Text(
                text = post.caption?:"nothing is in caption",
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )

            Spacer(modifier = Modifier.height(15.dp))

            //post image
            post.imageUrl?.let{imageUrl->

                AsyncImage(
                    model = imageUrl,
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "Post image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }

            //Action Button
            Row(modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {

                    //like button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(9.dp),
                        modifier = Modifier.clickable{
                            //user clicks like button
                            handlelike.invoke()
                            post.username?.let {it->
                                LikeunLikeViewModel.LikeUnLikePost(id=post.id,
                                    username = it)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like Post",
                            tint = if (isLiked.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(30.dp)
                        )

                        Text(
                            text = likescount.value.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }


                    //Comment Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(9.dp),
                        modifier = Modifier.clickable{
                            //navigate to comment button
                            onCommentClick(post.id)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.comment),
                            contentDescription = "Comment",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )


                        Text(text = post.numberOfComments?.toString()?:"0",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }


                }

            }

        }

     }
}
