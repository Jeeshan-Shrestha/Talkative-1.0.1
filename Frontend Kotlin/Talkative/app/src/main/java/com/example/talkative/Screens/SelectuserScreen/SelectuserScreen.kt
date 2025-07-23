package com.example.talkative.Screens.SelectuserScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.talkative.R
import com.example.talkative.Screens.homeScreen.HomeScreenViewModel
import com.example.talkative.model.selectuserResponse.Message
import com.example.talkative.model.selectuserResponse.SelectUserResponse
import com.example.talkative.navigation.TalkativeScreen


@Composable
fun SelectuserScreen(selectuser:SelectuserScreenViewmodel,
                     navController:NavController,
                     myname:String?) {

    //for loading animation
    val uiState= selectuser.state.collectAsState()

    val data:SelectUserResponse = selectuser.item

    LaunchedEffect(null) {
        selectuser.getData()
    }



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
                Column(modifier = Modifier.padding(5.dp)) {

                    LazyColumn(contentPadding = PaddingValues(10.dp)) {

                      items(data.message){msg->
                          CardView(msg){username->
                              Log.d("allout", "SelectuserScreen: $myname $username ")
                              navController.navigate(TalkativeScreen.HomeScreen.name+"/${myname}/${username}")
                          }
                      }
                    }
                }
            }
        }
    }
}






@Composable
fun CardView(data:Message,onClick : (String) -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        onClick ={
            onClick.invoke(data.username)
        }
        ){
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){

            Surface(modifier = Modifier
                .padding(13.dp)
                .size(100.dp),
                color = Color.Transparent){

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://scontent.fktm1-1.fna.fbcdn.net/v/t39.30808-6/473191485_2344820392544272_7483716841069894706_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=2Bd9jpG4DUsQ7kNvwFMm4bu&_nc_oc=Adm0YBwvO89UGois4CqxcDHwK4I09ASLeWik1gtQ76lBkb44xlTsFAIfgxNp7jCmXKN_ghIxWv6D-MU_els9lT-H&_nc_zt=23&_nc_ht=scontent.fktm1-1.fna&_nc_gid=NZXwP1oeKtbBX__SbAoO_w&oh=00_AfRiP0oYOWZNEbv1UFgm-ESdAvIL7lEQxGjEDwDHqvyjQA&oe=688538C0")
                        .crossfade(true)
                        .build(),
                    contentDescription = "user image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.clip(CircleShape)
                )
            }
            Text(
                text =data.username?:"unknown",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

}
}