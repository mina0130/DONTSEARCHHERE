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
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.w3c.dom.Text

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
                Surface(modifier = Modifier.fillMaxSize()){
                    Color.White
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleText(text = "Guess the Word")
                        Spacer(modifier = Modifier.height(500.dp))
                        keyBoard()

                    }
                }
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
    Surface(modifier = Modifier.fillMaxSize()){
        Color.White
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(70.dp))
            TitleText(text = "Guess the Word")
            Spacer(modifier = Modifier.height(500.dp))
            keyBoard()

        }
    }


}

@Composable
fun keyBoard(){
    Column(horizontalAlignment = CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center) {
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "A")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "B")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "C")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "D")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "E")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "F")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "G")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "H")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "I")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "J")
        }
        Row() {
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "K")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "L")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "M")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "N")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "O")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "P")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "Q")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "R")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "S")
        }
        Row(){
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "T")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "U")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "V")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "W")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "X")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "Y")
            keyBoardButton(onClick = { /*TODO*/ }, enabled = true, text = "Z")
        }
    }
}

@Composable
fun keyBoardButton(onClick: () -> Unit, enabled: Boolean, text: String){
    Button(modifier=Modifier.width(33.dp), onClick=onClick, enabled=enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta)){
        Text(text = text, textAlign = TextAlign.Center)
    }
    Spacer(modifier = Modifier.width(3.dp))
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
        var enabled = false
        if(!state.spinnable){
            enabled=true }
        if(state.spinnable){
            enabled=false
        }
    Spacer(modifier=Modifier.height(10.dp))
    NextButton(onClick = navigateFunction, enabled)


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
fun NextButton(onClick: () -> Unit, enabled: Boolean){
    Button(onClick=onClick, enabled=enabled,
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
        contentColor = Color.White),
    ) {
        Text(text="Start Guessing",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            fontSize = 20.sp)
    }
}


