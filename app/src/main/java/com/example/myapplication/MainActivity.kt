package com.example.myapplication

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier=Modifier.fillMaxSize(),
                    color=Color.White){

                var result by remember { mutableStateOf(1) }
                val imageResource=when(result){
                    1->R.drawable.wheel_200
                    2->R.drawable.wheel_100
                    3-> R.drawable.wheel_400
                    4->R.drawable.wheel_1000
                    5->R.drawable.wheel_500
                    else -> R.drawable.wheel_bankrupt
                }
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
                    Text(
                        text = "Wheel of Fortune",
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp, fontFamily = FontFamily.Cursive
                    )
                    Surface(
                        modifier = Modifier
                            .height(500.dp)
                            .width(200.dp)
                    ) {
                        Image(painter = painterResource(imageResource), contentDescription = "1")

                    }


                    Button(
                        onClick = { result = (1..5).random() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Spin the Wheel",
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    }
                }
                }
            }
        }
        }
    }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        var result by remember { mutableStateOf(1) }
        val imageResource=when(result){
            1->R.drawable.wheel_200
            2->R.drawable.wheel_100
            3-> R.drawable.wheel_400
            4->R.drawable.wheel_1000
            5->R.drawable.wheel_500
            else -> R.drawable.wheel_bankrupt
        }
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
            Surface(modifier = Modifier
                .height(500.dp)
                .width(200.dp)){
                Image(painter = painterResource(imageResource), contentDescription = result.toString())

            }


            Button(onClick = { result= (1..5).random()}, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black,
                contentColor = Color.White)) {
                Text(text="Spin the Wheel",
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp)
            }
            }


    }
}



