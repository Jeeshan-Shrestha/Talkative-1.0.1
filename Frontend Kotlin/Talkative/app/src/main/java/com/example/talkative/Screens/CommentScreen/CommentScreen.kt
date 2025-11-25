package com.example.talkative.screens.CommentScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.components.CustomTextField
import com.example.talkative.components.DropdownMenuWithDetails
import com.example.talkative.components.LoadingDialog
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBarWithBack
import com.example.talkative.model.GetAllCommentResponse.Comment
import com.example.talkative.navigation.TalkativeScreen
import com.example.talkative.utils.LoadingState
import kotlin.math.exp


@Composable
fun CommentScreen(navController: NavController,
                  AddCommentViewModel: AddCommentViewModel,
                  DeleteCommentViewModel:DeleteCommentViewModel,
                  postId: String?,
                  GetAllCommentViewModel: GetAllCommentViewModel) {

    val listState = rememberLazyListState()

    //context for toast
    val context = LocalContext.current

    val fetchedComments by GetAllCommentViewModel.comments.collectAsState()

    val uiStateforComment=  GetAllCommentViewModel.state.collectAsState()

    val uiStateforAddComment = AddCommentViewModel.state.collectAsState()

    val uiStateforDeleteComment = DeleteCommentViewModel.state.collectAsState()

    //comment state
    val comment = remember {
        mutableStateOf("")
    }


    val valid = remember(comment.value) {
        comment.value.trim().isNotEmpty() //if something is written then true
    }

    //to hide keyboard
    val keyboardController = LocalSoftwareKeyboardController.current



    LaunchedEffect(Unit){
        postId?.let {
            GetAllCommentViewModel.getAllComments(id = it)
        }
    }

    LaunchedEffect(uiStateforAddComment.value,uiStateforDeleteComment.value) {
        if (uiStateforAddComment.value == LoadingState.SUCCESS || uiStateforDeleteComment.value == LoadingState.SUCCESS) {
            postId?.let {
                GetAllCommentViewModel.getAllComments(id = it)
            }
            AddCommentViewModel.resetState()
            DeleteCommentViewModel.resetState()
            comment.value=""
        }
    }


    Scaffold(
        topBar = {
            TopBarWithBack(text = "Comments") {
                //when user press back button navigate to previous screen
                navController.popBackStack()
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

                if(uiStateforAddComment.value == LoadingState.LOADING || uiStateforComment.value == LoadingState.LOADING || uiStateforDeleteComment.value == LoadingState.LOADING){
                    LoadingDialog()
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 17.dp, horizontal = 1.dp)
                ) {

//                    if(showUserComment.va lue) {
//                        item {
//                            CommentCard(fetchedComments=
//                                Comment(
//                                    commentId = "abc",
//                                    commentText = pushedComment.value,
//                                    liked = false,
//                                    numberOfLikes = 0,
//                                    commentedBy = "abc",
//                                    avatar = null)
//                            )
//                        }
//                    }

                //use items and show comment
                    items(fetchedComments){
                        CommentCard(fetchedComments=it, openUserProfile = {username->
                            //open user profile
                            navController.navigate(TalkativeScreen.OtherUserProfileScreen.name+"/${username}") }){commentId->
                            DeleteCommentViewModel.Deletecomment(id = commentId)
                        }
                    }

                    //if user has made a comment he will see the value here

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
                        if(valid) {
                            keyboardController?.hide()
                            postId?.let {
                                AddCommentViewModel.addComment(
                                    postId = it,
                                    commentText = comment.value
                                )
                            }
                        }else{
                            Toast.makeText(context, "Add a Comment First", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

            }
        }
    }
}


//used to display comments username avatar etc
@Composable
fun CommentCard(fetchedComments: Comment,
                openUserProfile:(String)-> Unit,
                deleteComment:(String)-> Unit){

    var isLiked = remember {
        mutableStateOf(fetchedComments.liked)
    }
    var likesCount = remember {
        mutableStateOf(fetchedComments.numberOfLikes)
    }

    val handleLike ={
        isLiked.value=!isLiked.value
        likesCount.value = if(isLiked.value) likesCount.value + 1 else likesCount.value-1
    }

    //State for Deleting Comment
    val expanded = remember {
        mutableStateOf(false)
    }


    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .combinedClickable(
            onLongClick = {
                //onLong press we will delete comment only if the user has posted
                if(fetchedComments.ownProfile){
                    expanded.value=true
                }
            },
            onClick = {

            }
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top){
        //Avatar of user, we have to make this clickable , when user clicks avatar,
        //navigate user to the profile screen
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(fetchedComments.avatar?:"https://i.imgur.com/veVP6GL.png")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "Comment Avatar",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .clickable{
                    //opening user profile when avatar is clicked
                    if(!fetchedComments.ownProfile) {
                        openUserProfile(fetchedComments.commentedBy)
                    }
                }
        )

        //drop down menu for delete button
        DropdownMenuWithDetails(text = "Delete Comment",
            expanded = expanded.value,
            onClick = {
                //pass comment id
                deleteComment(fetchedComments.commentId)
            }) {
            expanded.value=false
        }

        Column(modifier = Modifier
            .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {

            //now making Row for like button
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                //displaying user name
                Text(
                    text = fetchedComments.commentedBy?:"unknown",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        //navigate to user profile
                        //for avatar and this use same  method
                        //goUserProfile.invoke
                        if(!fetchedComments.ownProfile) {
                            openUserProfile(fetchedComments.commentedBy)
                        }
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
            Text(text = fetchedComments.commentText?:"asdfajsdofajda",
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight)

        }

    }
}
