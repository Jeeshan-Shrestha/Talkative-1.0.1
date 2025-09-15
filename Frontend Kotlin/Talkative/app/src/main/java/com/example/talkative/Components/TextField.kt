package com.example.talkative.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun EmailField(
    email: MutableState<String>,
    labelid:String,
    imeAction: ImeAction = ImeAction.Default,
    onAction: KeyboardActions =KeyboardActions.Default,
    maxlines:Int=1,
    keyboardtype: KeyboardType = KeyboardType.Unspecified,
    isSingleLine:Boolean
){
    TextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text(text = labelid) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F4F4),
            focusedContainerColor = Color(0xFFF5F4F4)
        ),
        singleLine = isSingleLine,
        keyboardActions =onAction,
        maxLines = maxlines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardtype, imeAction = imeAction)
    )
}


@Composable
fun PasswordField(
    passwordState:MutableState<String>,
    labelid:String,
    imeAction: ImeAction= ImeAction.Done,
    onAction:KeyboardActions=KeyboardActions.Default,
    passwordvisibility:MutableState<Boolean>,
    maxlines:Int=1,
    keyboardtype: KeyboardType = KeyboardType.Unspecified

){

    val visualTransformation = if(passwordvisibility.value) VisualTransformation.None
    else
        PasswordVisualTransformation()

    TextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelid) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White), // Set the background color to white
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F4F4),
            focusedContainerColor = Color(0xFFF5F4F4)
        ),
        trailingIcon = {

            if(passwordvisibility.value){
                IconButton(onClick ={passwordvisibility.value= !passwordvisibility.value} ) {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = "icon")
                }
            }
            else{
                IconButton(onClick = {passwordvisibility.value =!passwordvisibility.value}) {
                    Icon(imageVector = Icons.Default.EnhancedEncryption, contentDescription = "icon")
                }
            }
        },
        maxLines = maxlines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardtype, imeAction = imeAction),
        visualTransformation = visualTransformation,
        keyboardActions = onAction,
        singleLine = true
    )
}

