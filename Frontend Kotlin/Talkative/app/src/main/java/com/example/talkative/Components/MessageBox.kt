package com.example.talkative.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//ifCurrent user
//ColorScheme primary
//else colorScheme surfaceVariant
@Composable
fun MessageBox(text:String="hello world",
               textColor: Color,
               color: Color= MaterialTheme.colorScheme.primary
){
    BoxWithConstraints {
        val maxwidth = maxWidth * 0.6f

        Card(
            modifier = Modifier
                .widthIn(max = maxwidth)
                .padding(2.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = color),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    style = TextStyle(
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}