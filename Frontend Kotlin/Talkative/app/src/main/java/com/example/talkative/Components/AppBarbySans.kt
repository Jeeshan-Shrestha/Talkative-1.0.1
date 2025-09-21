package com.example.talkative.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppBarbySans(
    title: String="Attendance",
    savebutton: Boolean=false,
    onSaveClicked:()-> Unit ={},
    icon: ImageVector?=null,
    onBackArrowClicked:()->Unit={}
) {

    TopAppBar(
        title = {
            Row(modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Start
                ,verticalAlignment = Alignment.CenterVertically) {
                Text(text = title,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold,
                        fontSize = 23.sp)
                )
            }
        },
        navigationIcon = {
            //navigation icon is shown at the front of the Screen

            if(icon!=null){
                IconButton(onClick = {
                    onBackArrowClicked.invoke()
                }){
                    Icon(imageVector = icon,
                        contentDescription = "back arrow",
                       // tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        },
        actions = {
            //show only on ShowAttendanceScreen
            if(savebutton){
                sansButton(text = "Save",
                    icon = true,
                    color = Color.Black,
                    textcolor = Color.White) {
                    onSaveClicked.invoke()
                }

            }

        }
    )
}