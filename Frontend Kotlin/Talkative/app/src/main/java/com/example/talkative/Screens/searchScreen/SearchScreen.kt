package com.example.talkative.screens.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.talkative.components.BottomBar
import com.example.talkative.components.TopBar
import com.example.talkative.components.UserCard
import com.example.talkative.model.MockData



@Composable
fun SearchScreen(navController: NavController= NavController(LocalContext.current)){

    val searchQuery = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController=navController)
        }
    ) {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            Column(modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                OutlinedTextField(value = searchQuery.value,
                    onValueChange = {searchQuery.value=it},
                    placeholder = { Text("Search for people") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search people")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified, imeAction = ImeAction.Search),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 1)

                //Suggested People
                PeopleContent()

            }

        }

    }
}


@Composable
fun PeopleContent(){
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)){

        item {
            Text(
                text = "Suggested for you",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }

        items(MockData.mockUsers){it->
            UserCard(user = it)
        }

    }
}