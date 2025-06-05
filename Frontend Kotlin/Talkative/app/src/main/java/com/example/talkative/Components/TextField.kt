package com.example.talkative.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkative.R

@Composable
fun SendField(
    modifier: Modifier,
    placeHolder:String,
    valueState: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onAction: KeyboardActions = KeyboardActions.Default,
    maxLines:Int=5,
    onClick : () -> Unit
){

    val backgroundColor= Color(0xFF304050)
    OutlinedTextField(
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.White,  // Cursor color
            focusedIndicatorColor = Color.Transparent,  // No border when focused
            unfocusedIndicatorColor = Color.Transparent,  // No border when not focused
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(15.dp)),
        placeholder = {
            Text(
                text = placeHolder,
                style = TextStyle(fontSize = 19.sp, color = Color(0xFFB0B0B0))
            )
        },
        value=valueState.value,
        onValueChange = {valueState.value=it},
        maxLines = maxLines,
        singleLine = false,
        textStyle = TextStyle(fontSize = 19.sp,
            color =Color.White),
        enabled = true,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType=keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        trailingIcon = {
           sansButton(text = "hehe") {
               onClick.invoke()
           }
        }
    )
}