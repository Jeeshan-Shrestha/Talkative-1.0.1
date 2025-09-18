package com.example.talkative.components

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R


@Composable
fun OnyourMind(){

    val newpost = rememberSaveable {
        mutableStateOf("")
    }

    val pathEffect =androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f,10f),0f)

    //handling images selected From gallery
    var selectedImage = rememberSaveable {
        mutableStateOf<String?>(null)
    }

    //context for toast
    val context = LocalContext.current


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
            selectedImage.value = it.toString()
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



    Card(modifier = Modifier
        .padding(start = 8.dp, end =8.dp)
        .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(10.dp)){

            Row(modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://i.imgur.com/WTFTkMh.jpeg")
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentDescription = "userimage",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                    )
                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    OutlinedTextField(
                        value = newpost.value,
                        onValueChange = {newpost.value=it},
                        placeholder = {Text("What's on Your mind?")},
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp)
                    )

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
                                        .heightIn(max=400.dp)
                                        .fillMaxWidth()
                                )

                                IconButton(
                                    onClick = { selectedImage.value = null },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        painter =painterResource(R.drawable.closeicon),
                                        tint = Color.Unspecified,
                                        contentDescription = "Remove image",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                    }

                    //Dotted Line
                    DrawComposable(pathEffect)


                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.clickable { /* Handle photo click */
                            permissionLauncher.launch(requiredPermission)}
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.Photo,
                                contentDescription = "Add Photo"
                            )
                            Text(
                                text = "Photo",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(4.dp),
//                            modifier = Modifier.clickable { /* Handle emoji click */ }
//                        ) {
//                            Icon(
//                                modifier = Modifier.size(30.dp),
//                                imageVector = Icons.Default.EmojiEmotions,
//                                contentDescription = "Add Emoji"
//                            )
//                            Text(
//                                text = "Emoji",
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }

                        sansButton(text = "Post",
                            color = Color.Black,
                            icon = false
                            ) {
                                //Handle Post
                            }




                    }

                }

            }

        }

    }
}



//dotted line
@Composable
fun DrawComposable(patheffect:androidx.compose.ui.graphics.PathEffect ){
    Canvas (modifier = Modifier
        .fillMaxWidth()
        .height(1.5.dp)) {
        drawLine(color = Color.Black,
            start = Offset(0f,0f),// x and y direction we are not moving so 0f
            end = Offset(size.width,0f),
            pathEffect = patheffect
        )
    }
}
