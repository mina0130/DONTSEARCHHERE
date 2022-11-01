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
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                WheelOfFortune()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        WheelOfFortune()
    }
}



@Composable
fun WheelOfFortune(){
    var result by remember { mutableStateOf(1) }
    val imageResource=when(result){
        1->R.drawable.wheel_200
        2->R.drawable.wheel_100
        3-> R.drawable.wheel_400
        4->R.drawable.wheel_1000
        5->R.drawable.wheel_500
        else -> R.drawable.wheel_bankrupt
    }
    var displayText: String
    when(result){
        1-> displayText="$200"
        2-> displayText="$100"
        3->displayText="$400"
        4->displayText="$1000"
        5->displayText="$500"
        else->displayText="Bankrupt"}


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
        Text(text = "Wheel of Fortune",
            textAlign = TextAlign.Center,
            fontSize = 60.sp, fontFamily = FontFamily.Cursive)
        Spacer(modifier = Modifier.height(30.dp))
        Surface(modifier = Modifier
            .height(300.dp)
            .width(300.dp)){
            Image(painter = painterResource(imageResource),
                contentDescription = result.toString(),
                contentScale = ContentScale.FillWidth)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = displayText,
            textAlign = TextAlign.Center,
            fontSize = 30.sp, fontFamily = FontFamily.SansSerif)
        Spacer(modifier = Modifier.height(70.dp))

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { result= (1..5).random()},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
                contentColor = Color.White),
            enabled=true) {
            Text(text="Spin the Wheel",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp)
        }
    }
}

        /*
                        var result by remember { mutableStateOf(1) }
                val imageResource=when(result){
                    1->R.drawable.wheel_200
                    2->R.drawable.wheel_100
                    3-> R.drawable.wheel_400
                    4->R.drawable.wheel_1000
                    5->R.drawable.wheel_500
                    else -> R.drawable.wheel_bankrupt
                }
                var displayText: String by remember {
                    mutableStateOf("")
                }
            when(result){
                    1-> displayText="$200"
                    2-> displayText="$100"
                    3->displayText="$400"
                    4->displayText="$1000"
                    5->displayText="$500"
                    else->displayText="Bankrupt"}

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
                    Text(text = "Wheel of Fortune",
                        textAlign = TextAlign.Center,
                        fontSize = 60.sp, fontFamily = FontFamily.Cursive)
                    Spacer(modifier = Modifier.height(30.dp))
                    Surface(modifier = Modifier
                        .height(300.dp)
                        .width(300.dp)){
                        Image(painter = painterResource(imageResource),
                            contentDescription = result.toString(),
                            contentScale = ContentScale.FillWidth)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = displayText,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp, fontFamily = FontFamily.SansSerif)
                    Spacer(modifier = Modifier.height(70.dp))
                    Spacer(modifier = Modifier.height(30.dp))

                   var flag=true
                   Button(onClick = { flag=false},
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
                            contentColor = Color.White), enabled=flag) {
                        Text(text="Spin the Wheel",
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp)
                    }
                    if(flag==false){
                        result= (1..5).random()
                        startActivity(intent)
                    }
                }
            }
        }
        val intent=Intent(this, GuessActivity::class.java)
        @Composable
        fun SpinButton(){
            var flag=true
         Button(onClick = {flag=false },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
                    contentColor = Color.White), enabled=flag) {
                Text(text="Spin the Wheel",
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp)
            }

        }
        * */

