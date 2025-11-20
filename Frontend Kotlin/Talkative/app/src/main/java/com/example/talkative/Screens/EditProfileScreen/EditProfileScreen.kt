package com.example.talkative.screens.EditProfileScreen

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
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
import com.example.talkative.model.customDataPassing.ProfileArgument
import com.example.talkative.navigation.TalkativeScreen
import androidx.core.net.toUri
import com.example.talkative.components.LoadingDialog
import com.example.talkative.utils.LoadingState
import kotlin.contracts.contract

@Composable
fun EditProfileScreen(navController: NavController= NavController(LocalContext.current),
                      editProfileViewModel: EditProfileViewModel){

    //from viewModel
    val uiState = editProfileViewModel.state.collectAsState()


    val context = LocalContext.current

    val userinfo = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<ProfileArgument>("profileArg")

//    Log.d("raichu", " $userinfo ") //this gives us avatar name coverImage bio



    val avatar = remember {
      //  mutableStateOf(userinfo?.avatar?:"https://i.imgur.com/veVP6GL.png")
       // mutableStateOf<Uri?>( (userinfo?.avatar?:"https://i.imgur.com/veVP6GL.png").toUri())
        mutableStateOf<Uri?>(null)
    }

    val name = remember {
        mutableStateOf(userinfo?.displayName?:"unknown")
    }

    val coverImage= remember {
      //  mutableStateOf(userinfo?.coverPhoto?:"https://images.unsplash.com/photo-1501785888041-af3ef285b470")
        //mutableStateOf<Uri?>((userinfo?.coverPhoto?:"https://images.unsplash.com/photo-1501785888041-af3ef285b470").toUri())
        mutableStateOf<Uri?>(null)
    }

    val bio = remember {
        mutableStateOf(userinfo?.bio?:"")
    }

    //requesting permission
    val requiredPermissioin = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    //gallery launcher
    val galleryLauncherforAvatar = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {uri->
        uri?.let{
            avatar.value = it
        }
    }

    val galleryLauncherforCoverImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {uri->
        uri?.let{
            coverImage.value = it
        }
    }

    //permission launcher
    val permissionLauncherforAvatar = rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestPermission()) {isGranted->

        if(isGranted){
            //if granted launch gallery
            galleryLauncherforAvatar.launch("image/*")
        }else{
            Toast.makeText(context, "Please enable permission first", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncherforCoverImage = rememberLauncherForActivityResult(
        contract =ActivityResultContracts.RequestPermission()) {isGranted->

        if(isGranted){
            //if granted launch gallery
            galleryLauncherforCoverImage.launch("image/*")
        }else{
            Toast.makeText(context, "Please enable permission first", Toast.LENGTH_SHORT).show()
        }
    }






    Scaffold(
        topBar = {
            AppBarbySans(title = "Edit Profile",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onSaveClicked = {
                    //when user Clicks Save Navigate to Profile screen
                    if(bio.value.isNotBlank() && name.value.isNotBlank()){
                        editProfileViewModel.PostImage(
                            displayName = name.value,
                            bio=bio.value,
                            avatar= avatar.value!!,
                            coverPhoto = coverImage.value!!){
                            navController.navigate(TalkativeScreen.ProfileScreen.name)
                        }
                    }else{
                        Toast.makeText(context, "Enter name and bio first", Toast.LENGTH_SHORT).show()
                    }
                },
                savebutton = true
            ) {
                ///pop back stack
                navController.popBackStack()
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

                        //user coverPhoto and change photo Butotn
                        Column (modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(modifier = Modifier
                                .height(230.dp)
                                .fillMaxWidth()) {

                                //cover image
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(coverImage.value?:userinfo?.coverPhoto?:"https://images.unsplash.com/photo-1501785888041-af3ef285b470")
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(R.drawable.placeholder),
                                    contentDescription = "userimage",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
//
//                            sansButton(icon = false,
//                                color = Color.White,
//                                textcolor = Color.Black,
//                                text = "Change Cover Photo"){
//
//                                //open gallery and change the photo
//
//                            }

                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.TopEnd
                                ) {

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        FilledTonalButton(
                                            onClick = {
                                                //edit cover photo
                                                permissionLauncherforCoverImage.launch(requiredPermissioin)
                                               // navController.navigate(TalkativeScreen.EditProfileScreen.name)
                                            },
                                            colors = ButtonDefaults.buttonColors(Color.White)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                tint = Color.Black,
                                                modifier = Modifier.size(16.dp),
                                                contentDescription = "edit Cover photo"
                                            )

                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                "Edit Cover Photo",
                                                style = TextStyle(color = Color.Black)
                                            )
                                        }
                                    }
                                }
                            }


                        }

                        //user avatar and change photo button
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(avatar.value?:userinfo?.avatar?:"https://i.imgur.com/veVP6GL.png")
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
                                text = "Change Profile Photo"){
                                //open gallery and change the cover photo
                                permissionLauncherforAvatar.launch(requiredPermissioin)
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

                        Spacer(modifier = Modifier.height(10.dp))

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
                                imeAction = ImeAction.Done,
                                maxlines = 3,
                                keyboardtype = KeyboardType.Unspecified,
                                isSingleLine = false
                            )
                        }


                    }
                }}
        }
        if(uiState.value== LoadingState.FAILED){
            Toast.makeText(context, "Failed,try again later", Toast.LENGTH_SHORT).show()
        }
        if(uiState.value== LoadingState.LOADING){
            LoadingDialog()
        }
    }

}


