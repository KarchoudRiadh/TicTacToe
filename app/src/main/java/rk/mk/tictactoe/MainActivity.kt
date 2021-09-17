package rk.mk.tictactoe

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    var gameRules: GameRules = GameRules()
    private lateinit var timerTv: TextView
    private lateinit var xScore: TextView
    private lateinit var xArrow: ImageView
    private lateinit var oScore: TextView
    private lateinit var oArrow: ImageView
    private lateinit var timer : CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        timerTv = findViewById<View>(R.id.timer) as TextView
        xScore = findViewById<View>(R.id.player_x_score) as TextView
        oScore = findViewById<View>(R.id.player_o_score) as TextView
        xArrow = findViewById<View>(R.id.x_player_arrow) as ImageView
        oArrow = findViewById<View>(R.id.o_player_arrow) as ImageView
        //Starting Timer
        timer = object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val f: NumberFormat = DecimalFormat("00")
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                timerTv.text = f.format(min) + ":" + f.format(sec)
            }

            override fun onFinish() {
                timerTv.text = "00:00"
                Toast.makeText(this@MainActivity, "Time is over! This point will go to player "+gameRules.activePlayer.playerName,Toast.LENGTH_LONG).show()
                gameRules.activePlayer.playerScore++
                //Refresh scores and restart the game
                oScore.text = gameRules.oPlayer.playerScore.toString()
                xScore.text = gameRules.xPlayer.playerScore.toString()
                xArrow.visibility = View.VISIBLE
                oArrow.visibility = View.INVISIBLE
                restartGame()
            }
        }.start()
    }

    //Called when a player taps
    fun playerClick(view: View) {
        val img: ImageView = view as ImageView
        val tappedImage: Int = img.tag.toString().toInt()

        // Restart the game if someone wins or the boxes are full
        if (!gameRules.gameActive) {
            restartGame()
        }
        //detect if that case is already clicked
        if (gameRules.gameState[tappedImage] == 2) {
            gameRules.counter++

            // check if its the last box
            if (gameRules.counter == 9) {
                // reset the game
                gameRules.gameActive = false
            }

            // mark this position
            gameRules.gameState[tappedImage] = gameRules.activePlayer.playerCode

            // this will give a motion
            // effect to the image
            img.translationY = -1000f

            // change the active player
            if (gameRules.activePlayer.playerCode == 0) {
                // O turn play
                img.setImageResource(R.drawable.o)
                gameRules.activePlayer = gameRules.xPlayer
                xArrow.visibility = View.VISIBLE
                oArrow.visibility = View.INVISIBLE
            } else {
                // X turn to play
                img.setImageResource(R.drawable.x)
                gameRules.activePlayer = gameRules.oPlayer
                xArrow.visibility = View.INVISIBLE
                oArrow.visibility = View.VISIBLE
            }
            img.animate().translationYBy(1000f).duration = 300
        }
        var flag = 0
        // Check if any player has won
        for (winPosition in gameRules.winPositions) {
            if (gameRules.gameState[winPosition[0]] == gameRules.gameState[winPosition[1]]
                && gameRules.gameState[winPosition[1]] == gameRules.gameState[winPosition[2]]
                && gameRules.gameState[winPosition[0]] != 2) {
                flag = 1
                timer.cancel()
                gameRules.gameActive = false
                xArrow.visibility = View.VISIBLE
                oArrow.visibility = View.INVISIBLE
                gameRules.counter = 0
                if (gameRules.gameState[winPosition[0]] == 0) {
                    gameRules.oPlayer.playerScore++
                    oScore.text = gameRules.oPlayer.playerScore.toString()
                    Toast.makeText(this@MainActivity, "Player O scored 1 point. Congrats! ",Toast.LENGTH_LONG).show()
                } else {
                    gameRules.xPlayer.playerScore++
                    xScore.text = gameRules.xPlayer.playerScore.toString()
                    Toast.makeText(this@MainActivity, "Player X scored 1 point. Congrats! ",Toast.LENGTH_LONG).show()
                }
                restartGame()
            }
        }
        // set the status if the match draw
        if (gameRules.counter == 9 && flag == 0) {
            timer.cancel()
            gameRules.counter = 0
            xArrow.visibility = View.VISIBLE
            oArrow.visibility = View.INVISIBLE
            Toast.makeText(this@MainActivity, "Draw! Lets try again",Toast.LENGTH_LONG).show()
        }
    }

    // restart the game
    private fun restartGame() {
        timer.start()
        gameRules.gameActive = true
        //Player X always starts first
        gameRules.activePlayer = gameRules.xPlayer
        for (i in gameRules.gameState.indices) {
            gameRules.gameState[i] = 2
        }
        // remove all the images from the boxes inside the grid
        (findViewById<View>(R.id.case_1_1) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_1_2) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_1_3) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_2_1) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_2_2) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_2_3) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_3_1) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_3_2) as ImageView).setImageResource(0)
        (findViewById<View>(R.id.case_3_3) as ImageView).setImageResource(0)
    }


}