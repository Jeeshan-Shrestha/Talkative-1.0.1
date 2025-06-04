package com.example.talkative.Screens.homeScreen

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
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val msg by viewModel.messages.collectAsState()

    val status by viewModel.status.collectAsState()

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

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold { it ->

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
                                        MessageBox(text = smth.text)
                                    }
                                }

                                is Message.Sent -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        MessageBox(text = smth.text)
                                    }
                                }
                            }
                        }
                    }
                    // Connection status
                    Text(
                        text = "Status: $status",
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row {
                        SendField(
                            modifier = Modifier.weight(1f)
                                .padding(6.dp),
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

