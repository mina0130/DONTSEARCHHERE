package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.StringBuilder
import kotlin.random.Random

data class WheelOfFortuneUiState(
    val wheelImage : Int = R.drawable.wheel_1000,
    val wheelResult : String = "",
    val playerBalance : Int = 0,
    val spinnable : Boolean = true
)

class WheelOfFortuneViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(WheelOfFortuneUiState())
    val uiState = _uiState.asStateFlow()

    fun spinWheel() {
        val result = (1..6).random();
        val imageResource=when(result){
            1->R.drawable.wheel_200
            2->R.drawable.wheel_100
            3-> R.drawable.wheel_400
            4->R.drawable.wheel_1000
            5->R.drawable.wheel_500
            else -> R.drawable.wheel_bankrupt
        }
        val displayText: String = when(result){
            1->  "$200"
            2->  "$100"
            3-> "$400"
            4-> "$1000"
            5-> "$500"
            else-> "Bankrupt"
        }
        _uiState.update {
            it.copy(
                wheelImage = imageResource,
                wheelResult = displayText,
                playerBalance = 0,
                spinnable = false
            )
        }
    }
}
data class GuessStateUI(
    var wordProgress: String = "",
    var playerBalance: Int = 0,
    val lives: Int = 5,
    val started: Boolean = false,
    val won: Boolean = false,
    val lost: Boolean= false,
val wordDrawn: String = "hi",
val categoryDrawn: String = "hi")

class GuessStateViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(GuessStateUI())
    val uiState = _uiState.asStateFlow()

    fun DrawWord(){ val started=true
        val Index: Int = Random.nextInt(0, Words.places.size)
        val category: String
        if(Index<=16){
        category="places"
        }
        else {
            category="names"
        }
        val wordDrawn = Words.places[Index]
        var dashes: java.lang.StringBuilder = StringBuilder("")
        for(i in 1..wordDrawn.length)
            dashes.append("_ ")

        fun CheckWord(guess: String){
            if (guess.equals(wordDrawn)){
              val  wordProgress = wordDrawn
                _uiState.update{it.copy(wordProgress = wordProgress)}
            }


        }
        _uiState.update { it.copy(started=started, wordDrawn = wordDrawn, categoryDrawn = category, wordProgress = dashes.toString()) }
    }
    fun TypeLetter(letter: String){
val enabled = false

    }
}
