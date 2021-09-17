package rk.mk.tictactoe

class GameRules {
    var gameActive = true
    var xPlayer = Player("X",0, 1)
    var oPlayer = Player("O",0, 0)
    var activePlayer = xPlayer
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    var counter = 0
}

class Player(
    var playerName: String, var playerScore: Int, var playerCode: Int
)