package com.example.myapplication

import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class WheelOfFortuneUiState(
    val wheelImage : Int = R.drawable.wheel_1000,
    val wheelResult : String = "",
    val playerBalance : Int = 0,
    val spinnable : Boolean = true
)


class WheelOfFortuneViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(WheelOfFortuneUiState())
    val uiState = _uiState.asStateFlow()

    fun NextButton(){

    }

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
    val lives: Int = 5
)

class GuessStateViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(GuessStateUI())
    val uiState = _uiState.asStateFlow()

    fun Guess(){

        //_uiState.update {
            //it.copy(
            // wordProgress =
        //}
    }

}