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
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Illuminant.A
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.w3c.dom.Text

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
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val viewModel = WheelOfFortuneViewModel()
    MyApplicationTheme {
        WheelOfFortune(viewModel.uiState.collectAsState().value,
            spinWheelFunction = { viewModel.spinWheel() },
            navigateFunction = {}, newGame = {})
    }
}

@Preview
@Composable
fun GuessPreview(){
    Surface(modifier = Modifier.fillMaxSize()){
        Color.White
        Column(horizontalAlignment = CenterHorizontally) {
            Spacer(modifier = Modifier.height(70.dp))
            TitleText(text = "Guess the Word")
            Spacer(modifier = Modifier.height(70.dp))
           WordProgressText(text = "______")
            Text("Category: ")
            Spacer(modifier = Modifier.height(100.dp))
            val currentText = remember {
                mutableStateOf(TextFieldValue())
            }
            TextField(value = currentText.value,
                onValueChange = {currentText.value=it})
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier=Modifier.height(20.dp)){
                Text(text="100")
                Spacer(modifier = Modifier.width(50.dp))
                Text(text="5 ")
                Image(painterResource(id = R.drawable.download),
                    contentDescription = null, contentScale = ContentScale.FillHeight)
            }
            Spacer(modifier = Modifier.height(100.dp))
            keyBoard(onClick = {/*TODO*/}, state=WheelOfFortuneUiState())
        }
    }


}

@Composable
fun Guessing(state: WheelOfFortuneUiState, onDraw: ()-> Unit, onType: (Char)-> Unit,
             navigateBack: ()-> Unit, CheckWord: (String)->Unit){
    Surface(modifier = Modifier.fillMaxSize()){
        Color.White
        Column(horizontalAlignment = CenterHorizontally) {
            Spacer(modifier = Modifier.height(70.dp))
            TitleText(text = "Guess the Word")
            Spacer(modifier = Modifier.height(40.dp))
            DrawButton(DrawWordFunction = onDraw, enabled=!state.started)
            Spacer(modifier = Modifier.height(30.dp))
            Text(text="Tried Letters: " + state.triedLetters, textAlign = TextAlign.Left)
            Spacer(modifier = Modifier.height(30.dp))
            var displayText : String = ""
            if(state.won){
                displayText="You Won!"
            }
            else if(state.lost){
                displayText="You Lost!"
            }
            else {
                displayText=state.wordProgress
            }
            WordProgressText(text = displayText)
            if(!state.lost && !state.won){
                Text(text="Category: "+state.categoryDrawn)
            }
           else{
                Text(text="Word was: "+state.wordDrawn)
            }
            Spacer(modifier = Modifier.height(10.dp))
            if(state.started  && !state.pressable && !(state.won or state.lost)){
                SpinAgainButton(onCLick = navigateBack, text="Spin Again")
            }
            else if(state.won or state.lost){
                SpinAgainButton(onCLick = navigateBack, text = "New Game")
            }
            else{
                Spacer(modifier = Modifier.height(20.dp))
            }

            GuessButton(onClick = CheckWord, enabled=state.pressable)
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier=Modifier.height(20.dp)){
                Text(text="$ "+ state.playerBalance.toString())
                Spacer(modifier = Modifier.width(40.dp))
                if(state.lives>=0){
                    Text(text=state.lives.toString()+" ")
                }
               else{
                    Text(text="0")
                }
                Image(painterResource(id = R.drawable.download),
                    contentDescription = null, contentScale = ContentScale.FillHeight)
            }
            Spacer(modifier = Modifier.height(40.dp))
            keyBoard(onClick = onType, state = state)
        }
    }
}

@Composable
fun keyBoard(onClick: (Char) -> Unit, state: WheelOfFortuneUiState){
    Column(horizontalAlignment = CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center) {
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'A')
            keyBoardButton(onClick = onClick, enabled=state.pressable, text = 'B')
            keyBoardButton(onClick = onClick, enabled=state.pressable, text = 'C')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'D')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'E')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'F')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'G')
            keyBoardButton(onClick = onClick, enabled =state.pressable, text = 'H')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'I')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'J')
        }
        Row() {
            keyBoardButton(onClick = onClick, enabled =state.pressable, text = 'K')
            keyBoardButton(onClick = onClick, enabled =state.pressable, text = 'L')
            keyBoardButton(onClick = onClick, enabled =state.pressable, text = 'M')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'N')
            keyBoardButton(onClick = onClick, enabled =state.pressable, text = 'O')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'P')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'Q')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'R')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'S')
        }
        Row(){
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'T')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'U')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'V')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'W')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'X')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'Y')
            keyBoardButton(onClick = onClick, enabled = state.pressable, text = 'Z')
        }
    }
}

@Composable
fun WordProgressText(text: String){
    Text(text = text,
        textAlign = TextAlign.Center,
        fontSize = 40.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Cursive)
}
@Composable
fun keyBoardButton(onClick: (Char) -> Unit, enabled: Boolean, text: Char){
    Button(modifier=Modifier.width(35.dp), onClick={onClick(text)}, enabled=enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta)){
        Text(text = text.toString(), textAlign = TextAlign.Center)
    }

    Spacer(modifier = Modifier.width(3.dp))
}
@Composable
fun WheelOfFortune(state : WheelOfFortuneUiState, spinWheelFunction: () ->Unit,
navigateFunction: ()-> Unit, newGame: () -> Unit){
    Column(
        Modifier
            .fillMaxSize()
            .absolutePadding(
                10.dp,
                100.dp,
                10.dp, 0.dp
            ), horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top)
    {
        if (state.won or state.lost) {
            NewGameButton(newGame)
        }
        else {
            TitleText("Wheel of Fortune")
            Spacer(modifier = Modifier.height(30.dp))
            Wheel(image = state.wheelImage)
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = state.wheelResult,
                textAlign = TextAlign.Center,
                fontSize = 30.sp, fontFamily = FontFamily.SansSerif
            )

            SpinButton(onClick = spinWheelFunction, enabled = state.spinnable)
            var enabled = false
            if (!state.spinnable) {
                enabled = true
            }
            if (state.spinnable) {
                enabled = false
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (!state.won && !state.lost) {
                NextButton(onClick = navigateFunction, enabled)
            }

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

@Composable
fun DrawButton(DrawWordFunction: ()-> Unit, enabled: Boolean){
    Button(onClick=DrawWordFunction,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
            contentColor = Color.White), enabled = enabled
    ) {
        Text(text="Draw word",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            fontSize = 20.sp)
    }
}

@Composable
fun SpinAgainButton(onCLick: ()-> Unit, text: String){
    Button(modifier = Modifier.height(30.dp), onClick = onCLick, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta)){
        Text(text=text, fontSize = 10.sp)
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun GuessButton(onClick: (String) -> Unit, enabled: Boolean){

    val currentText = remember {
        mutableStateOf("")
    }

    Column(horizontalAlignment = CenterHorizontally){
        TextField(value = currentText.value,
            onValueChange = {currentText.value=it},
            singleLine = true)

        Button(onClick={onClick(currentText.value)},  enabled=enabled,
            colors=ButtonDefaults.buttonColors(backgroundColor = Color.Black,
            contentColor = Color.White)){
            Text(text="Try", fontSize = 10.sp)
        }
    }

}
@Composable
fun NewGameButton(onClick: () -> Unit){
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)) {
            Text(text = "Start New Game", fontFamily = FontFamily.Cursive,
                fontSize = 60.sp, textAlign= TextAlign.Center)
        }


}



