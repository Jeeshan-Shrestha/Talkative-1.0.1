package com.example.talkative.components

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.max
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.DataBase.UserInformation
import com.example.talkative.DataBase.UserViewModel
import com.example.talkative.R
import com.example.talkative.model.MockData
import com.example.talkative.screens.CreatePost.CreatePostViewmodel
import com.example.talkative.utils.LoadingState


@Composable
fun CreatePostDialouge(
    onDismiss: () -> Unit ={},
    userViewModel: UserViewModel=hiltViewModel(),
    createPostViewmodel: CreatePostViewmodel,
    onPost:()->Unit={} //to close dialouge
    ){

    //context for toast
    val context = LocalContext.current

    //data from viewmodel
    val userInformation=userViewModel.userDetails.collectAsState()
//    val userInformation = UserInformation(
//        username = "sanskar",
//        displayName = "Great",
//        avatar = ""
//    )


    val content = rememberSaveable {
        mutableStateOf("")
    }

    val characterlimit= 260
    val charactersRemaining = characterlimit - content.value.length


    //handling images selected From gallery
    val selectedImage = rememberSaveable { mutableStateOf<Uri?>(null) }

    //for drawing dotted line
    val pathEffect =androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)


    //From view model to display toast message
    val uiState= createPostViewmodel.state.collectAsState()



    // Permission to request based on Android version
    val requiredPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedImage.value = it
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // If granted, launch gallery
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Please enable permission first", Toast.LENGTH_SHORT).show()
        }
    }



    Dialog(onDismissRequest = {onDismiss()}) {

        Card(modifier = Modifier
            .heightIn(max = 600.dp, min = 300.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 9.dp)) {

            Column(modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)){


                //Header
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween){

                    Text(text = "Create Post",
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.headlineSmall)

                    IconButton(onClick = {onDismiss() }) {
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = "Close Dialouge")
                    }
                }

                //user Info
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    verticalAlignment = Alignment.CenterVertically){

                    //Avatar
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userInformation.value?.avatar?:"https://i.imgur.com/veVP6GL.png")
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentDescription = "userimage",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                    )

                    Column{
                        //displayName
                        Text(
                            text = userInformation.value?.displayName?:"Unknown",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        //userName
                        Text(
                            text = userInformation.value?.username?:"Unknown",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                    }
                }

                //Post content
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    OutlinedTextField(
                        value = content.value,
                        onValueChange = { if(it.length <= characterlimit) content.value=it  },
                        placeholder = {Text("What's Happening dear?")},
                        modifier = Modifier
                            .heightIn(min = 120.dp)
                            .fillMaxWidth(),
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End){

                        //Chip box
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(text =charactersRemaining.toString())
                            },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = if (charactersRemaining <20) MaterialTheme.colorScheme.errorContainer
                                        else MaterialTheme.colorScheme.secondaryContainer))

                    }
                    //selected Image to Preview
                    selectedImage.value?.let { imageUrl->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box {

                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(R.drawable.placeholder),
                                    contentDescription = "userimage",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .heightIn(max = 400.dp)
                                        .fillMaxWidth()
                                )

                                IconButton(
                                    onClick = { selectedImage.value = null },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                      painter = painterResource(R.drawable.closeicon),
                                        contentDescription = "Remove image",
                                        modifier = Modifier.size(25.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                        }
                    }
                    DrawComposable(pathEffect)
                }


                //for adding and posting photo
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable { /* Handle photo click */
                            permissionLauncher.launch(requiredPermission)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = Icons.Default.Photo,
                            contentDescription = "Add Photo"
                        )
                        Text(
                            text = "Add Photo",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    sansButton(text = "Post",
                        color = Color.Black,
                        icon = false) { //Handle Post

                        if(content.value.isNotBlank() && selectedImage.value !=null){
//            onPost.invoke() we wont be using lamda let's call view model here only
                            selectedImage.value?.let{uri->
                                createPostViewmodel.PostImage(caption = content.value, imageUri = uri){
                                    //navigate to home Screen
                                    Toast.makeText(context, "Uploded Successfully", Toast.LENGTH_SHORT).show()
                                    onPost.invoke()
                                }
                            }

                        }else{
                            Toast.makeText(context, "Please add Image and caption", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if(uiState.value== LoadingState.FAILED){
                        Toast.makeText(context, uiState.value.message, Toast.LENGTH_SHORT).show()
                    }
                    if(uiState.value== LoadingState.LOADING){
                        LoadingDialog()
                    }
                }

            }


        }

    }

}