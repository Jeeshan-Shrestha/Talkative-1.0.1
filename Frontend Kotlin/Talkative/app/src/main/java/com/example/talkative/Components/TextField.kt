package com.example.talkative.Components


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SendField(
    modifier: Modifier,
    valueState: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onAction: KeyboardActions = KeyboardActions.Default
){

    OutlinedTextField(
        modifier = modifier,
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
        keyboardActions = onAction
    )
}