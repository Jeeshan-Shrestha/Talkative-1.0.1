package com.example.talkative.Screens.signupScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.talkative.Components.SendField
import com.example.talkative.R
import com.example.talkative.navigation.TalkativeScreen

@Preview
@Composable
fun SignUpScreen(NavController: NavController = NavController(LocalContext.current)){
    val name = remember {
        mutableStateOf("")
    }

    val password1 = remember {
        mutableStateOf("")
    }
    
    val password2 = remember {
        mutableStateOf("")
    }


    val valid = remember(name.value,password1.value,password2.value){
        name.value.trim().isNotEmpty() && password1.value.trim().isNotEmpty() && password2.value.trim().isNotEmpty()
    }

    val CheckPassword = remember(password1.value , password2.value){
        password1.value == password2.value
    }



    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold {
        Box{
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
                color = Color.Transparent){
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Text(text = "SignUp To Use Talkative",
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(15.dp),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 30.sp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    SendField(
                        modifier = Modifier,
                        maxLines = 1,
                        placeHolder = "Username",
                        valueState = name,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        enableButton = false){
                        //nothing
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    SendField(
                        modifier = Modifier,
                        maxLines = 1,
                        placeHolder = "New Password",
                        valueState = password1,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        enableButton = false){
                        //nothing
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    SendField(
                        modifier = Modifier,
                        maxLines = 1,
                        placeHolder="Confirm Password",
                        valueState = password2,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        onAction = KeyboardActions {
                            if(!valid)
                                return@KeyboardActions
                            keyboardController?.hide() }) {
                        if(!valid){
                            Toast.makeText(context, "Enter The Missing Fields", Toast.LENGTH_SHORT).show()
                        }
                        else if (!CheckPassword){
                            Toast.makeText(context,"New Password and Confirm Password should be same",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Log.d("pussy", "AddUserNameScreen: ${name.value}")
                            NavController.navigate(TalkativeScreen.HomeScreen.name+"/${name.value}")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.padding(10.dp)) {
                        Text(
                            modifier = Modifier.padding(start =5.dp),
                            text = "Already Have an Account?",
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start =5.dp)
                                .clickable {
                                    NavController.navigate(TalkativeScreen.AdduserName.name)
                                },
                            text = "Sign In",
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                }

            }

        }
    }
}