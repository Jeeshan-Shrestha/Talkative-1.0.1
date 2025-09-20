package com.example.talkative.components

import androidx.compose.material3.Button
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun sansButton(
    color:Color= Color(0xFF1490CF),
    textcolor:Color=Color.White,
    modifier: Modifier =Modifier,
    text:String="hello",
    icon:Boolean=true,
    onClick: () -> Unit={}) {
    // Create Class Button
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp)
    ) {
        Text(text = text,
            modifier = Modifier.padding(0.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = textcolor)
        if(icon) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "button"
            )
        }
    }
}