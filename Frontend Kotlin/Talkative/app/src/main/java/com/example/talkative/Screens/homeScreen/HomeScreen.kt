package com.example.talkative.Screens.homeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.talkative.Components.MessageBox
import com.example.talkative.Components.SendField
import com.example.talkative.Components.sansButton
import com.example.talkative.R
import com.example.talkative.model.Message


@Composable
fun HomeScreen(viewModel: HomeScreenViewModel,
               username:String?) {

    Log.d("April", "HomeScreen: we are inside home screen ")

    val msg by viewModel.messages.collectAsState()

    val status by viewModel.status.collectAsState()

//    var username2 by remember {
//        mutableStateOf("")
//    }

    val message = remember {
        mutableStateOf("")
    }

    val valid = remember(message.value) {
        message.value.trim().isNotEmpty()
    }

    val listState = rememberLazyListState()

    LaunchedEffect(msg.size) {
        if (msg.isNotEmpty()) {
            listState.animateScrollToItem(msg.size - 1)
        }
    }

    //connecting the websocket when the Screen is opened
   LaunchedEffect(Unit){
       username?.let {
           viewModel.ConnectAndObserve(username=username)
       }
   }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold {
        Box {

            Image(
                painter = painterResource(R.drawable.katnuprny),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Background Image",
                contentScale = ContentScale.FillBounds
            )


            Surface(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                color = Color.Transparent
            ) {
                Column(verticalArrangement = Arrangement.Bottom) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        state = listState,
                        reverseLayout = false,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(msg) { smth ->
                            when (smth) {
                                is Message.Recieved -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Column(modifier = Modifier.padding(7.dp),
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(start =5.dp),
                                                text = "${smth.sender}",
                                                color = Color.White.copy(alpha = 0.7f)
                                            )
                                            MessageBox(
                                                text = smth.text,
                                                color = Color(0xFF253453)
                                            )
                                        }
                                    }
                                }

                                is Message.Sent -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Column(modifier = Modifier.padding(7.dp),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.Center
                                        ){
                                            Text(text = "${username}",
                                                modifier = Modifier.padding(end = 5.dp),
                                                color = Color.White.copy(alpha = 0.7f)
                                            )
                                            MessageBox(text = smth.text,
                                                color = Color(0xFF0B88D8)
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                    // Connection status
                    Text(
                        text = "Status: $status",
                        modifier = Modifier.padding(8.dp),
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Row {
                        SendField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(6.dp),
                            placeHolder = "Type your message here",
                            valueState = message,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Default,
                            onAction = KeyboardActions {
                                if (!valid) {
                                    return@KeyboardActions
                                }
                                keyboardController?.hide()
                            }
                        ){
                            viewModel.SendMessages(message.value)
                            message.value=""
                        }
//                        sansButton(text = "Send") {
//                            viewModel.SendMessages(message.value)
//                            message.value = ""
//                        }
                    }
                }
            }
        }
    }
}

