package com.example.talkative.screens.searchScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.talkative.components.BottomBar
import com.example.talkative.components.CreatePostDialouge
import com.example.talkative.components.EmptyText
import com.example.talkative.components.SimpleLoadingAnimation
import com.example.talkative.components.TopBar
import com.example.talkative.components.UserCard
import com.example.talkative.model.SearchResponse.Message
import com.example.talkative.model.SearchResponse.SearchResponse
import com.example.talkative.navigation.TalkativeScreen
import com.example.talkative.screens.CreatePost.CreatePostViewmodel
import com.example.talkative.utils.LoadingState


@Composable
fun SearchScreen(navController: NavController= NavController(LocalContext.current),
                 SearchViewModel: SearchViewModel,
                 FollowUnFollowViewmodel: FollowUnFollowViewModel,
                 createPostViewmodel: CreatePostViewmodel){

    val showPostDialouge = remember { mutableStateOf(false) }

    val searchQuery = remember {
        mutableStateOf("")
    }

    //making sure something is written before user hits search
    val valid = remember(searchQuery.value) {
        searchQuery.value.trim().isNotEmpty()  //kei na kei lekhya xa vane true hunxa
    }

    //now to hide the keyboard
   val  keyboardcontroller= LocalSoftwareKeyboardController.current

    //From view model to display toast message
    val uiState= SearchViewModel.state.collectAsState()

    //data from backend
    val getUsers: SearchResponse = SearchViewModel.item

    //context
    val context = LocalContext.current

    // when the user opens the app
    val query = searchQuery.value
    LaunchedEffect(key1 = query) {
        if(query.isEmpty()){
            SearchViewModel.SearchUser("")
        }else{
            kotlinx.coroutines.delay(500)
            SearchViewModel.SearchUser(query)
        }
    }


    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController=navController){
                showPostDialouge.value=true
            }
        }
    ) {it->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            Column(modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                //search bar

                OutlinedTextField(value = searchQuery.value,
                    onValueChange = {searchQuery.value=it},
                    placeholder = { Text("Search for people") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search people")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified, imeAction = ImeAction.Search),
                    shape = RoundedCornerShape(12.dp),
                    keyboardActions = KeyboardActions{
                        if(!valid)
                            return@KeyboardActions
                        keyboardcontroller?.hide()

                    },
                    maxLines = 1)

                //Suggested People
                PeopleContent(getUsers = getUsers.message,
                    FollowUnFollowViewmodel=FollowUnFollowViewmodel ,
                    uiState = uiState){username->
                    navController.navigate(TalkativeScreen.OtherUserProfileScreen.name+"/${username}")
                }

            }

            if(showPostDialouge.value){
                CreatePostDialouge(onDismiss = {
                    showPostDialouge.value=false
                },
                    createPostViewmodel = createPostViewmodel,
                    onPost = {
                        //we will handle post logic here
                        showPostDialouge.value=false
                    })
            }

        }

    }
}


@Composable
fun PeopleContent(getUsers: List<Message>,
                  FollowUnFollowViewmodel: FollowUnFollowViewModel,
                  uiState: State<LoadingState>,
                  openUserProfile:(String)-> Unit){
//simple loading animation
    SimpleLoadingAnimation(uiState=uiState)

    //if backend sends null then there is no user so
    if(getUsers.isEmpty()){
        EmptyText(text = "No user found.")
    }

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

        items(getUsers){it->
            UserCard(user = it, onCardClick = {username->
                //navigate to view profile screen
                openUserProfile.invoke(username)}){username->
                FollowUnFollowViewmodel.FollowUnfollowuser(username = username)
            }
        }

    }
}