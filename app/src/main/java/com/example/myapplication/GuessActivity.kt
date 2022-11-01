package com.example.myapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityGuessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Composable
        fun GameLayout(lines: String) {
            Text(text = lines,
                textAlign = TextAlign.Center,
                fontSize = 30.sp)
        }
        fun setGame(){
            var lives: Int
            var points: Int
            var won: Boolean
            var word: String
            val randomIndex: Int
         }
@Composable
         fun generateLines(word: String) : String{
             val lineGenerator = StringBuilder()
             val lines=word.length
             for(i in 1..lines){
                 lineGenerator.append("_ ")
             }
           val wordLines=lineGenerator.toString()
             GameLayout(lines =wordLines )
             return wordLines
        }
       fun playGame(){

       }
    }

}
@Preview
@Composable
fun GamePrev(){
    Text(text = "_ _ _ _ _ _ _",
        textAlign = TextAlign.Center,
        fontSize = 10.sp)

}


