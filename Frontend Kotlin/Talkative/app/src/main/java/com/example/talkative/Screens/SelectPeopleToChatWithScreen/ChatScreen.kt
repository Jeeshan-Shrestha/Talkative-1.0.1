package com.example.talkative.screens.SelectPeopleToChatWithScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.DataBase.UserViewModel
import com.example.talkative.R
import com.example.talkative.components.CustomTextField
import com.example.talkative.components.MessageBox
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBarForChatScreen
import com.example.talkative.model.ChatScreenReqAndRes.Message
import com.example.talkative.utils.LoadingState

@Composable
fun ChatScreen(
    ChatViewModel:ChatViewModel,
    displayName: String?,
    avatar: String?,
    UserViewModel: UserViewModel,
    navController: NavController,
    receiver:String? //the person who will be receiving the message
){

    val messagesFromViewModel=ChatViewModel.messages.collectAsState()
    val status=ChatViewModel.status.collectAsState()

    val listState= rememberLazyListState()

    val message = remember {
        mutableStateOf("")
    }

    val valid= remember(message.value){
        message.value.trim().isNotEmpty()
    }

    //to hide keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    //getting own username from database
    val dataFromDataBase=UserViewModel.userDetails.collectAsState()
    val ownUserName=dataFromDataBase.value?.username


    //using launched effect to connect to websocket
//    LaunchedEffect(Unit){
//        ChatViewModel.connect()
//    }
//
//    LaunchedEffect(receiver) {
//        ChatViewModel.resetMessages()
//    }
    LaunchedEffect(receiver,ownUserName){
        if(!receiver.isNullOrEmpty()){
            //connect websocket
            ownUserName?.let {
                ChatViewModel.initChat(chatPartner = receiver, ownUsername = it)
                ChatViewModel.resetMessages()
            }
        }
    }

    //auto scroll when new messages comes
    LaunchedEffect(messagesFromViewModel.value.size){
        if(messagesFromViewModel.value.isNotEmpty()){
            listState.animateScrollToItem(messagesFromViewModel.value.size-1)
        }
    }


    Scaffold(topBar = {
        TopBarForChatScreen(userName = receiver,
            displayName = displayName,
            avatar = avatar) {
            //navigate to previous screen
            navController.popBackStack()
        }
    }) {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()){

            Column(verticalArrangement = Arrangement.Bottom){

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState,
                    reverseLayout = false,
                    verticalArrangement = Arrangement.Bottom) {

                    items(messagesFromViewModel.value) {item->
                        when(item){
                            is Message.Received->{
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start){

                                    Column(modifier = Modifier.padding(7.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start){

//                                            Text(
//                                                modifier = Modifier.padding(start = 5.dp),
//                                                text = item.sender,
//                                                color = Color.Black.copy(alpha=0.7f))
                                        Row {
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(avatar?:"https://i.imgur.com/veVP6GL.png")
                                                    .crossfade(true)
                                                    .build(),
                                                placeholder = painterResource(R.drawable.placeholder),
                                                contentDescription = "Comment Avatar",
                                                contentScale = ContentScale.FillBounds,
                                                modifier = Modifier
                                                    .padding(top = 5.dp)
                                                    .size(42.dp)
                                                    .clip(CircleShape)
                                            )

                                            MessageBox(text =item.content,
                                                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                color = Color.Gray)
                                        }

                                    }

                                }
                            }
                            is Message.Sent -> {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End){

                                    Column(modifier = Modifier.padding(7.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.End){

//                                        Text(
//                                            modifier = Modifier.padding(start = 5.dp),
//                                            text = ownUserName?:"Unknown",
//                                            color = Color.Black.copy(alpha=0.7f)
//                                        )
                                        MessageBox(text =item.content,
                                            color = Color.Black,
                                            textColor = MaterialTheme.colorScheme.onPrimary)
                                    }

                                }

                            }
                        }
                    }

                }
                //WebSocket Status
                Text(
                    text="Status: ${status.value}",
                    modifier = Modifier.padding(8.dp),
                    color = Color.Black.copy(alpha = 0.7f)
                )
                //Text Field to type message
                Row {
                    CustomTextField(
                        state = message,
                        labelid = "Type your message here",
                        imeAction = ImeAction.Default,
                        isSingleLine = true,
                        keyboardtype = KeyboardType.Text,
                        onAction = KeyboardActions{
                            if(!valid)
                                return@KeyboardActions
                            keyboardController?.hide()
                        },
                        trailingIcon = true,
                        onSend = {
                            //send message to the user
                            if(valid) {
                                //Send  Message
                                receiver?.let {
                                    ChatViewModel.sendMesage(content = message.value.trim(), receiver = receiver)
                                }
                                message.value=""
                            }
                        }
                    )
                }
            }

        }
    }
}