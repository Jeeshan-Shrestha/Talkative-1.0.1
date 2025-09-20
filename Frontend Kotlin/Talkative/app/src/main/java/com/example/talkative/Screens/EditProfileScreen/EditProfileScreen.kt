package com.example.talkative.screens.EditProfileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.components.AppBarbySans
import com.example.talkative.components.BottomBar
import com.example.talkative.components.CreatePostDialouge
import com.example.talkative.components.CustomTextField
import com.example.talkative.components.sansButton
import com.example.talkative.model.MockData

@Composable
fun EditProfileScreen(navController: NavController= NavController(LocalContext.current)){

    val user = MockData.mockUser

    val imagelink = remember {
        mutableStateOf(user.avatar?:"https://i.imgur.com/WTFTkMh.jpeg")
    }

    val name = remember {
        mutableStateOf(user.name)
    }

    val username= remember {
        mutableStateOf(user.username)
    }

    val bio = remember {
        mutableStateOf(user.bio?:"")
    }
    val website = remember {
        mutableStateOf(user.website?:"")
    }


    Scaffold(
        topBar = {
            AppBarbySans(title = "Edit Profile",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                savebutton = true
            ) {
                ///pop back stack
                navController.popBackStack()
            }
        },
        bottomBar = {
            BottomBar(navController = navController){

            }
        }
    ){it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            //for fields
            Column(modifier = Modifier
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)){

                LazyColumn {
                    item {

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imagelink.value)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.placeholder),
                                contentDescription = "userimage",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(CircleShape)
                            )

                            sansButton(icon = false,
                                color = Color.White,
                                textcolor = Color.Black,
                                text = "Change Photo"){

                                //open gallery and change the photo

                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "Name",
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            CustomTextField(state = name,
                                labelid = "Your name",
                                imeAction = ImeAction.Next,
                                maxlines = 1,
                                keyboardtype = KeyboardType.Unspecified,
                                isSingleLine = true
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "Username",
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            CustomTextField(
                                state = username,
                                labelid = "You",
                                imeAction = ImeAction.Next,
                                maxlines = 1,
                                keyboardtype = KeyboardType.Unspecified,
                                isSingleLine = true
                            )
                        }


                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "Bio",
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            CustomTextField(
                                state = bio,
                                labelid = "Bio",
                                imeAction = ImeAction.Next,
                                maxlines = 3,
                                keyboardtype = KeyboardType.Unspecified,
                                isSingleLine = false
                            )
                        }


                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "Website",
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            CustomTextField(
                                state = website,
                                labelid = "yourwebsite.com",
                                imeAction = ImeAction.Done,
                                maxlines = 1,
                                keyboardtype = KeyboardType.Unspecified,
                                isSingleLine = true
                            )
                        }

                    }
                }}
        }
    }

}


