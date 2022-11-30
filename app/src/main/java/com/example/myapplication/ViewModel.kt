package com.example.myapplication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

data class WheelOfFortuneUiState(
    val wheelImage : Int = R.drawable.wheel_1000,
    val wheelResult : String = "",
    val playerBalance : Int = 0,
    val spinnable : Boolean = true,
    var wordProgress: String = "",
    val lives: Int = 5,
    val started: Boolean = false,
    val won: Boolean = false,
    val lost: Boolean= false,
    val wordDrawn: String = "",
    val categoryDrawn: String = "",
    var triedLetters: String="",
val pressable: Boolean = false
)

class WheelOfFortuneViewModel() : ViewModel() {
    private val _uiState = mutableStateOf(WheelOfFortuneUiState())
    val uiState: State<WheelOfFortuneUiState> = _uiState
var imageResource: Int = 0
    var result: Int = 0
    fun spinWheel() {
        result = (1..6).random()
        imageResource=when(result){
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
        val pressable: Boolean
        if(_uiState.value.started){
            pressable = true
        }
        else{
            pressable = false
        }
        if(result == 6){
            playerBalance = 0
        }
        _uiState.value = _uiState.value.copy(
                wheelImage = imageResource,
                wheelResult = displayText,
                playerBalance = playerBalance,
                spinnable = false,
                pressable = pressable
            )
        }
    var wordDrawn: String = ""
    var wordProgress: StringBuilder = StringBuilder("")
    fun DrawWord(){
        val Index: Int = Random.nextInt(0, Words.places.size)
        val category: String
        if(Index<=17){
            category="places"
        }
        else if(Index<=27){
            category="names"
        }
        else if(Index<=32) {
            category="food"
        }
        else{
            category="Animals"
        }
       wordDrawn = Words.places[Index]
        for(i in 1..wordDrawn.length){
            wordProgress.append("_")}

       _uiState.value = _uiState.value.copy(
            started=true,
            wordDrawn = wordDrawn,
            categoryDrawn = category,
            wordProgress = wordProgress.toString(),
            pressable = true) }

    var playerBalance: Int = 0
    fun UpdateBalance(){
        val increment = when(result){
            1->200
            2->100
            3->400
            4->1000
            5->500
            else->0
        }
        System.out.println(playerBalance)
        if(increment!=0){
            playerBalance=playerBalance+increment
        }
        else if(increment==0){
            playerBalance=0
        }
        _uiState.value =_uiState.value.copy(playerBalance=playerBalance)
    }
    var lives: Int =5

    fun CheckWord(guess: String){
        if (guess.equals(wordDrawn, ignoreCase = true)){
            for(i in 0..wordDrawn.length-1){
                if (wordProgress[i].equals('_')){
                    UpdateBalance()
                }
            }
            _uiState.value = _uiState.value.copy(wordProgress = wordDrawn)
            CheckWin() }
    else {
        lives=lives-1
            CheckLose()
    }
        _uiState.value =  _uiState.value.copy(lives=lives, pressable = false, spinnable = true)  }

    fun CheckWin(){
        if(_uiState.value.wordProgress.equals(_uiState.value.wordDrawn, ignoreCase = true))
            _uiState.value =  _uiState.value.copy(won=true, spinnable = false, triedLetters = "") }

    fun NewGame(){
        wordProgress.clear()
        triedLetterstemp.clear()
        lives=5
        playerBalance=0
        wordDrawn=""
        _uiState.value =   _uiState.value.copy(started = false,
            spinnable = true, wordDrawn = "", playerBalance = 0,
            lives=5, won=false, lost = false, wordProgress = "",
            categoryDrawn = "", wheelResult = "0", triedLetters = "") }

    var lost: Boolean=false

    fun CheckLose(){
       if(lives<=0){
           lost=true
           _uiState.value = _uiState.value.copy(lost=lost, spinnable = false, triedLetters = "") }
       }

    val triedLetterstemp: StringBuilder= StringBuilder()
    fun LetterPress(letter: Char){
        if(!triedLetterstemp.contains(letter)){
        var flag = false
        for(i in 0..wordDrawn.length-1) {
            System.out.println(wordDrawn.get(i))
            if (wordDrawn.toUpperCase()[i] == letter) {
                flag = true
                UpdateBalance()
                wordProgress.setCharAt(i, letter)
            }
        }
            triedLetterstemp.append(letter)
            _uiState.value =  _uiState.value.copy(wordProgress=wordProgress.toString(),
                lives=lives, pressable = false, spinnable = true, triedLetters = triedLetterstemp.toString())
        if(!flag){
            lives=lives-1
            CheckLose()
            _uiState.value =  _uiState.value.copy(wordProgress=wordProgress.toString(),
                lives=lives, pressable = false, spinnable = true, triedLetters = triedLetterstemp.toString())
        }
        else{
            CheckWin()
        }

        }
        else{

        }

    }
}

