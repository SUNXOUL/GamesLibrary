package com.sagrd.GamesLibrary.ui.game

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sagrd.GamesLibrary.domain.model.Category
import com.sagrd.facturasapp.Nav.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gamesScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavController
)
{
    Scaffold (
        topBar = { TopAppBar(title = { Text(text = "GAME LIBRARY") },
            modifier = Modifier.shadow(8.dp),
            navigationIcon = {
                Icon(imageVector = Icons.Filled.Games, contentDescription ="" )
            }
        )
        },
        content = ({
            Games(viewModel)
        }),
        bottomBar = {
            Row (horizontalArrangement = Arrangement.End, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()){
                FloatingActionButton(onClick = { navController.navigate(route = AppScreens.SecondScreen.route) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription ="Add" )
                }
            }
        }
    )

}
@Composable
fun Games(
    viewModel: GameViewModel = hiltViewModel() ,
    categoryviewModel: CategoryViewModel = hiltViewModel()
)
{
    val games by viewModel.games.collectAsStateWithLifecycle()
    val categories by categoryviewModel.categories.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
    ) {
        items(categories) { category ->
            Text(text = category.name, modifier =  Modifier.padding(10.dp) )
            Divider()
            LazyRow (modifier = Modifier.fillMaxWidth()){
                items(games.filter { it.categoryid==category.categoryid }) { game ->
                   Column (horizontalAlignment = Alignment.CenterHorizontally){
                       Card(modifier = Modifier
                           .padding(8.dp)
                           .shadow(5.dp)
                       )
                       {
                           AsyncImage(
                               model = Uri.parse(game.image),
                               contentDescription = null,
                               modifier = Modifier
                                   .size(200.dp)
                                   .padding(5.dp)
                           )
                       }
                       Text(text = "${game.name}",modifier = Modifier.padding(8.dp))
                   }
                }
            }
        }
    }
}
