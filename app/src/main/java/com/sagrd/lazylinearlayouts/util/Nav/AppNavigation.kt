package com.sagrd.facturasapp.Nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sagrd.GamesLibrary.ui.game.GameFormScreen
import com.sagrd.GamesLibrary.ui.game.GameViewModel
import com.sagrd.GamesLibrary.ui.game.gamesScreen

@Composable
fun AppNavigation(
    viewModel: GameViewModel = hiltViewModel(),
)
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.FirstScreen.route
    ) {
        //Home Screen
        composable(AppScreens.FirstScreen.route) {
           gamesScreen(navController = navController)
        }
        composable(AppScreens.SecondScreen.route) {
            GameFormScreen(navController = navController)
        }
    }
}


