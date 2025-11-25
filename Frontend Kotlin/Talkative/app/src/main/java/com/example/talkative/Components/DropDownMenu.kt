package com.example.talkative.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuWithDetails(expanded: Boolean,
                            text:String="Logout",
                            onClick:()-> Unit,
                            onDismiss:()-> Unit) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss
        ) {

//            // First section
//            DropdownMenuItem(
//                text = { Text("Change password") },
//                leadingIcon = { Icon(Icons.Outlined.Password, contentDescription = "change password") },
//                onClick = {  }
//            )

            DropdownMenuItem(
                text = { Text(text) },
                leadingIcon = {
                    Icon(
                        Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = "logout"
                    )
                },
                onClick = { onClick.invoke() }
            )
            HorizontalDivider()
        }
    }



