package com.example.tictactoegame


import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoegame.R

class MainActivity : AppCompatActivity() {

    private lateinit var tictactoeBoard: Array<Array<Button>>
    private var isXTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.tvStatusTurn)
        val restartButton = findViewById<Button>(R.id.restartGameButton)
        tictactoeBoard = arrayOf(
            arrayOf(findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3)),
            arrayOf(findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6)),
            arrayOf(findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9))
        )

        for (row in tictactoeBoard) {
            for (button in row) {
                button.setOnClickListener {
                    if (button.text.isEmpty()) {
                        button.text = if (isXTurn) "X" else "O"

                        if (rowVictoryCheck() || columnVictoryCheck() || diagonalVictoryCheck()) {
                            statusText.text = if (isXTurn) "X Wins!" else "O Wins!"
                            disableAllButtons()
                            restartButton.visibility = Button.VISIBLE
                        } else if (isBoardFull()) {
                            statusText.text = "It's a Draw!"
                            restartButton.visibility = Button.VISIBLE
                        } else {
                            isXTurn = !isXTurn
                            statusText.text = if (isXTurn) "X's Turn" else "O's Turn"
                        }
                    }
                }
            }
        }

        restartButton.setOnClickListener {
            restartGame()
            isXTurn = true
            statusText.text = "X's Turn"
            restartButton.visibility = Button.GONE
        }
    }

    private fun isBoardFull(): Boolean {
        for (row in tictactoeBoard) {
            for (button in row) {
                if (button.text.isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    private fun rowVictoryCheck(): Boolean {
        for (row in tictactoeBoard) {
            if (row[0].text == row[1].text && row[1].text == row[2].text && row[0].text.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    private fun columnVictoryCheck(): Boolean {
        for (col in 0..2) {
            if (tictactoeBoard[0][col].text == tictactoeBoard[1][col].text &&
                tictactoeBoard[1][col].text == tictactoeBoard[2][col].text &&
                tictactoeBoard[0][col].text.isNotEmpty()) {
                return true
            }
        }
        return false
    }

    private fun diagonalVictoryCheck(): Boolean {
        if (tictactoeBoard[0][0].text == tictactoeBoard[1][1].text &&
            tictactoeBoard[1][1].text == tictactoeBoard[2][2].text &&
            tictactoeBoard[0][0].text.isNotEmpty()) {
            return true
        }

        if (tictactoeBoard[0][2].text == tictactoeBoard[1][1].text &&
            tictactoeBoard[1][1].text == tictactoeBoard[2][0].text &&
            tictactoeBoard[0][2].text.isNotEmpty()) {
            return true
        }

        return false
    }

    private fun disableAllButtons() {
        for (row in tictactoeBoard) {
            for (button in row) {
                button.isEnabled = false
            }
        }
    }

    private fun restartGame() {
        for (row in tictactoeBoard) {
            for (button in row) {
                button.text = ""
                button.isEnabled = true
            }
        }
    }
}