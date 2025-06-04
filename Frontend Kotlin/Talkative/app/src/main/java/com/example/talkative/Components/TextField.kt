package com.example.talkative.Components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
    valueState: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onAction: KeyboardActions = KeyboardActions.Default,
    onClick : () -> Unit
){
    val backgroundColor= Color(0xFF304050)

    OutlinedTextField(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(15.dp)),
        placeholder = {
            Text("Message")
        },
        value=valueState.value,
        onValueChange = {valueState.value=it},
        maxLines = 5,
        singleLine = false,
        textStyle = TextStyle(fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground),
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