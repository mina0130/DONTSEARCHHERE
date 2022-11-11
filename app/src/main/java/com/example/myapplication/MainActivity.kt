package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel = WheelOfFortuneViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                WheelOfFortuneApp(viewModel)
            }
        }
    }
}

@Composable
fun WheelOfFortuneApp(viewModel: WheelOfFortuneViewModel){
    MyApplicationTheme {
        val startRoute = "start"
        val guessRoute = "guess"
        val navigationController = rememberNavController()
        val state = viewModel.uiState.collectAsState()
        NavHost(navController = navigationController,
            modifier = Modifier.fillMaxSize(),
            startDestination = startRoute
        ) {
            composable(route = startRoute) {
                WheelOfFortune(state = state.value, 
                    spinWheelFunction = {
                        viewModel.spinWheel()
                    }, navigateFunction = { navigationController.navigate(guessRoute)}, newGame ={viewModel.NewGame()})
            }
            composable(route = guessRoute) {
                Guessing(state = state.value, onDraw={viewModel.DrawWord()},
                onType={viewModel.LetterPress(it)}, navigateBack={navigationController.navigate(startRoute)},
                    CheckWord={viewModel.CheckWord(it)})
            }
        }
    }
}
