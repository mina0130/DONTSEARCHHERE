package com.example.myapplication

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    val viewModel = WheelOfFortuneViewModel()
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
                      
                    }, navigateFunction = { navigationController.navigate(guessRoute)})
            }
            
            composable(route = guessRoute) {
                Text(text = "Hello")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = WheelOfFortuneViewModel()
    MyApplicationTheme {
        WheelOfFortune(viewModel.uiState.collectAsState().value,
            spinWheelFunction = {viewModel.spinWheel()},
            navigateFunction = {})
    }
}

@Preview
@Composable
fun GuessPreview(){
    TitleText(text = "Guess the Word")
    Spacer(modifier=Modifier.height(20.dp))

}

@Composable
fun keyBoard(){

}

@Composable
fun keyBoardButton(){
    //Button()
}
@Composable
fun WheelOfFortune(state : WheelOfFortuneUiState, spinWheelFunction: () ->Unit,
navigateFunction: ()-> Unit){
    Column(
        Modifier
            .fillMaxSize()
            .absolutePadding(
                10.dp,
                100.dp,
                10.dp, 0.dp
            ), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        TitleText("Wheel of Fortune")
        Spacer(modifier = Modifier.height(30.dp))
        Wheel(image = state.wheelImage)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = state.wheelResult,
            textAlign = TextAlign.Center,
            fontSize = 30.sp, fontFamily = FontFamily.SansSerif)

        SpinButton(onClick = spinWheelFunction, enabled = state.spinnable)
if(!state.spinnable){
    Spacer(modifier=Modifier.height(10.dp))
    NextButton(onClick = navigateFunction)
}

    }
}
@Composable
fun TitleText(text: String){
    Text(text = text,
        textAlign = TextAlign.Center,
        fontSize = 60.sp, fontFamily = FontFamily.Cursive)
}


@Composable
fun Wheel(image : Int) {
    Surface(modifier = Modifier
        .height(300.dp)
        .width(300.dp)){
        Image(painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth)
    }
}

@Composable
fun SpinButton(onClick: () -> Unit, enabled: Boolean) {
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
            contentColor = Color.White),
        enabled = enabled) {
        Text(text="Spin the Wheel",
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp)
    }
}

@Composable
fun NextButton(onClick: () -> Unit){
    Button(onClick=onClick,
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
        contentColor = Color.White),
    ) {
        Text(text="Start Guessing",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            fontSize = 20.sp)
    }
}
@Composable
fun GuessTheWord(state: GuessStateUI){
TitleText(text = "Guess the Word")

}

