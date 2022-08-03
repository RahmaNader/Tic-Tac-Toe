package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    enum class Turn
    {
        X,
        O
    }

    private var firstTurn = Turn.O
    private var currentTurn = Turn.O

    private var xScore = 0
    private var oScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(O))
        {
            oScore++
            result("O Wins!")
        }
        else if(checkForVictory(X))
        {
            xScore++
            result("X Wins!")
        }

        if(fullBoard())
        {
            result("Draw")
        }

    }

    private fun checkForVictory(s: String): Boolean
    {
        
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String)
    {
        val message = "\nO : $oScore\n\nX : $xScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Restart")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard()
    {
        for(button in boardList)
        {
            button.text = ""
        }

        if(firstTurn == Turn.X)
            firstTurn = Turn.O
        else if(firstTurn == Turn.O)
            firstTurn = Turn.X

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(currentTurn == Turn.X)
        {
            button.text = O
            currentTurn = Turn.O
        }
        else if(currentTurn == Turn.O)
        {
            button.text = X
            currentTurn = Turn.X
        }
        setTurnLabel()
    }

    private fun setTurnLabel()
    {
        var turnText = ""
        if(currentTurn == Turn.O)
            turnText = "Turn on X"
        else if(currentTurn == Turn.X)
            turnText = "Turn on O"

        binding.turnTV.text = turnText
    }

    companion object
    {
        const val O = "O"
        const val X = "X"
    }

}