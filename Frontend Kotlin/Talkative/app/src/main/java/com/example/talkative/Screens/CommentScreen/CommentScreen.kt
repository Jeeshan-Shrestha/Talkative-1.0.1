package com.example.talkative.screens.CommentScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.components.CustomTextField
import com.example.talkative.components.TopBarWithBack


@Composable
fun CommentScreen() {

    val listState = rememberLazyListState()

    //comment state
    val comment = remember {
        mutableStateOf("")
    }

    val valid = remember(comment.value) {
        comment.value.trim().isNotEmpty() //if something is written then true
    }

    //to hide keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopBarWithBack(text = "Comments") {
                //when user press back button navigate to previous screen
            }
        }
    ) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier
                .imePadding() //using this so text field slides up before the keyboard
                .padding(5.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 17.dp, horizontal = 1.dp)
                ) {
                //use items and show comments
                    item {
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()
                        CommentCard()

                    }
                }

                //add comment text field
                CustomTextField(
                    state = comment,
                    labelid = "Add a comment",
                    imeAction = ImeAction.Default,
                    isSingleLine = true,
                    onAction = KeyboardActions{
                        if(!valid)
                            return@KeyboardActions
                        keyboardController?.hide()
                    },
                    trailingIcon = true,
                    onSend = {
                        //send comment
                    }
                )

            }
        }
    }
}


//used to display comments username avatar etc
@Composable
fun CommentCard(){

    var isLiked = remember {
        mutableStateOf(true)
    }
    var likesCount = remember {
        mutableStateOf(32)
    }

    val handleLike ={
        isLiked.value=!isLiked.value
        likesCount.value = if(isLiked.value) likesCount.value + 1 else likesCount.value-1
    }


    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),

        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
        //onLong press we will delete comment
        ){
        //Avatar of user, we have to make this clickable , when user clicks avatar,
        //navigate user to the profile screen
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://i.imgur.com/veVP6GL.png")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "Comment Avatar",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = 10.dp)
                .size(42.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier
            .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {

            //now making Row for like button
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                //displaying user name
                Text(
                    text = "Sanskar",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        //navigate to user profile
                        //for avatar and this use same  method
                        //goUserProfile.invoke
                    })

                //now making Row for like button
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable{
                        //like un like button
                        handleLike.invoke()
                    }
                ) {

                    //like button and count
                    Icon(
                        imageVector = if (isLiked.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like Post",
                        tint = if (isLiked.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = likesCount.value.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }


            }
            //comment content
            Text(text = "asdfajsdofajda",
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight)

        }

    }
}
