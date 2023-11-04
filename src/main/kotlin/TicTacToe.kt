import java.awt.*
import java.awt.event.*
import javax.swing.*

class TicTacToe: JFrame(), ActionListener {
    private val textField: JLabel
    private val titlePanel : JPanel
    private val buttonPanel : JPanel
    private var player1 : Boolean = false
    private  val buttons : Array<Array<JButton>>

    init {
        title = "TicTacToe"
        size = Dimension(600,600)
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        val screen = Toolkit.getDefaultToolkit().screenSize
        val x = (screen.width - width)/2
        val y = ((screen.height - height)/2)/2
        setLocation(x,y)
        isVisible = true

        textField = JLabel()
        textField.background = Color(25, 25, 25)
        textField.foreground = Color(25, 255, 0)
        textField.font = Font("Ink Free",Font.BOLD,60)
        textField.horizontalAlignment = JLabel.CENTER
        textField.text = "TIC TAC TOE"
        textField.isOpaque = true

        titlePanel = JPanel()
        titlePanel.layout = BorderLayout()
        titlePanel.setBounds(0,0,600,100)
        titlePanel.add(textField)
        add(titlePanel,BorderLayout.NORTH)

        buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(3,3)
        buttonPanel.background = Color(68,144,243)
        add(buttonPanel)

        buttons = Array(3){ Array(3){ JButton() } }
        for(i in buttons.indices){
            for(j in buttons[i].indices){
                buttons[i][j].addActionListener(this)
                buttons[i][j].font = Font("Ink Free",Font.BOLD,100)
                buttons[i][j].background = Color(250, 184, 92)
                buttonPanel.add(buttons[i][j])
            }
        }
        firstTurn()

    }
    private fun firstTurn() {
        player1 = (0..1).random() == 0

    }
    private var gameOver = false

    override fun actionPerformed(e: ActionEvent?) {
        if (!gameOver) { // Only allow button clicks if the game is not over
            for (i in buttons.indices) {
                for (j in buttons[i].indices) {
                    if (e != null) {
                        if (e.source == buttons[i][j]) {
                            if (player1) {
                                if (buttons[i][j].text.isEmpty()) {
                                    buttons[i][j].text = "X"
                                    buttons[i][j].foreground = Color(25, 25, 25)
                                    player1 = false
                                    checkWinner()
                                }
                            } else {
                                if (buttons[i][j].text.isEmpty()) {
                                    buttons[i][j].text = "O"
                                    buttons[i][j].foreground = Color(25, 25, 25)
                                    player1 = true
                                    checkWinner()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun checkWinner() {
        // rows
        for (i in buttons.indices) {
            if (buttons[i][0].text == buttons[i][1].text &&
                buttons[i][0].text == buttons[i][2].text &&
                buttons[i][0].text.isNotEmpty()
            ) {
                announceWinner(buttons[i][0].text)
                return
            }
        }
        // Check columns
        for (j in buttons.indices) {
            if (buttons[0][j].text == buttons[1][j].text &&
                buttons[0][j].text == buttons[2][j].text &&
                buttons[0][j].text.isNotEmpty()
            ) {
                announceWinner(buttons[0][j].text)
                return
            }
        }
        // Check diagonals
        if (buttons[0][0].text == buttons[1][1].text &&
            buttons[0][0].text == buttons[2][2].text &&
            buttons[0][0].text.isNotEmpty()
        ) {
            announceWinner(buttons[0][0].text)
            return
        }

        if (buttons[0][2].text == buttons[1][1].text &&
            buttons[0][2].text == buttons[2][0].text &&
            buttons[0][2].text.isNotEmpty()
        ) {
            announceWinner(buttons[0][2].text)
            return
        }
        // Check for a draw
        var isDraw = true
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                if (buttons[i][j].text.isEmpty()) {
                    isDraw = false
                    break
                }
            }
        }
        if (isDraw) {
            announceDraw()
        }
    }

    private fun announceDraw() {
        textField.text = "It's a draw!"
        gameOver = true // Set the game to be over
        resizeTextField(600, 600) // Enlarge the text field
        // Start the timer to reset the game after 3 seconds
        val resetTimer = Timer(3000) { e ->
            resetGame()
            resizeTextField(100, 100) // Reset the text field size
            (e.source as Timer).stop() // Stop the timer after resetting
        }
        resetTimer.isRepeats = false // Ensure the timer only runs once
        resetTimer.start()
    }

    private fun announceWinner(winner: String) {
        textField.text = "Player $winner wins!"
        gameOver = true // Set the game to be over
        resizeTextField(600, 600) // Enlarge the text field
        // Start the timer to reset the game after 3 seconds
        val resetTimer = Timer(3000) { e ->
            resetGame()
            resizeTextField(100, 100) // Reset the text field size
            (e.source as Timer).stop()
        }
        resetTimer.isRepeats = false
        resetTimer.start()
    }

    private fun resizeTextField(width: Int, height: Int) {
        textField.preferredSize = Dimension(width, height)
        textField.font = Font("Ink Free", Font.BOLD, 60)
        revalidate() // Force the frame to re-layout components
    }

    private fun resetGame() {
        textField.text = "Tic-Tac-Toe" // Clear the winner/draw message
        gameOver = false // Reset the game over flag
        // Clear the buttons and start a new game
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].text = ""
            }
        }
        firstTurn()
    }
}