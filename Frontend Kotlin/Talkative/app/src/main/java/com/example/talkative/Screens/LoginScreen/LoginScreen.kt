package com.example.talkative.screens.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.talkative.R
import com.example.talkative.Talkative
import com.example.talkative.components.EmailField
import com.example.talkative.components.PasswordField
import com.example.talkative.components.sansButton
import com.example.talkative.navigation.TalkativeScreen


@Composable
fun LoginScreen(navController: NavController= NavController(LocalContext.current)){

    //state for managing text in the field
    val email = rememberSaveable {
        mutableStateOf("")
    }

    val password = rememberSaveable{
        mutableStateOf("")
    }

    //to show password
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }

    val valid = remember (email.value,password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() //if its empty it will written false
    }

    val keyboardController = LocalSoftwareKeyboardController.current //we can hide keyboard

    val context = LocalContext.current


    Scaffold {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Column(modifier = Modifier
                .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 30.dp)) {

//                    Text("Talkative",
//                        style = TextStyle(fontSize = 50.sp),
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black)

                    Icon(painter = painterResource(R.drawable.talk),
                        contentDescription = "Talkative")

                    Text(
                        text = "Connect with friends and share your moments",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    //making Card View
                    Card(modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {

                        Column(modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)) {

                            Text(
                                text ="Welcome Back",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "Sign in to your account to continue",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )

                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                            //TextField
                            EmailField(
                                email = email,
                                labelid = "Enter your email or Username",
                                imeAction = ImeAction.Next,
                                keyboardtype = KeyboardType.Email,
                                isSingleLine = true
                            )



                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                            PasswordField(
                                passwordState = password,
                                labelid = "Enter your Password",
                                imeAction = ImeAction.Done,
                                onAction = KeyboardActions {
                                    if(!valid) { //checking whether the inputs are not empty
                                        return@KeyboardActions
                                    }
                                    //use lamda to send data
                                    keyboardController?.hide()
                                },
                                passwordvisibility = passwordVisibility,
                                maxlines = 1,
                                keyboardtype = KeyboardType.Password
                            )

                            sansButton(text = "Login",
                                color = Color.Black,
                                icon = false,
                                modifier = Modifier.fillMaxWidth()) {
                                //on click we move to next screen
                                if(!valid){
                                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                                }else{
                                    navController.navigate(TalkativeScreen.HomeScreen.name)
                                }
                            }
                            TextButton (
                                onClick = {
                                    //lets navigate to signup screen
                                    navController.navigate(TalkativeScreen.SignUpScreen.name)
                                },
                                modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Don't have an account? Sign up",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }
                    }

                    Text(
                        text = "By continuing, you agree to our Terms of Service and Privacy Policy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 32.dp)
                    )

                }

            }

        }
    }
}