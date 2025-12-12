package games.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeGUI {

    private JFrame frame;
    private JButton[][] buttons;
    private int startingPlayer = 1;
    private int currentPlayer = 1;
    private JLabel currentPlayerLabel;
    private JLabel winnerLabel;

    public TicTacToeGUI() {
        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setResizable(false);

        // Use a layered pane so lines are behind buttons
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 300));

        // board drawing panel
        JPanel boardPanel = createBoardPanel();
        layeredPane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);

        // button grid panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 0, 300, 300);

        buttons = new JButton[3][3];
        createButtons(buttonPanel);

        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        createCurrentPlayerLabel();

        createWinnerLabel();

        createResetButton();

        frame.add(layeredPane);
        frame.setVisible(true);

        while (true) {
            checkForWin();
        }
    }

    private void createButtons(JPanel buttonPanel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.addActionListener(this::gridButtonPressed);
                buttons[i][j] = button;
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));

                int w = getWidth();
                int h = getHeight();

                // vertical lines
                g2.drawLine(w/3, 0, w/3, h);
                g2.drawLine(2*w/3, 0, 2*w/3, h);

                // horizontal lines
                g2.drawLine(0, h/3, w, h/3);
                g2.drawLine(0, 2*h/3, w, 2*h/3);
            }
        };

        boardPanel.setBounds(0, 0, 300, 300);
        return boardPanel;
    }

    private void createCurrentPlayerLabel() {
        currentPlayerLabel = new JLabel("Current Player: " + currentPlayer);
        currentPlayerLabel.setBounds(0, 320, 125, 25);
        frame.add(currentPlayerLabel);
    }

    private void createWinnerLabel() {
        winnerLabel = new JLabel("Winner: ");
        winnerLabel.setBounds(175, 320, 100, 25);
        frame.add(winnerLabel);
    }

    private void createResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(100, 350, 70, 25);
        resetButton.addActionListener(this::restartGame);
        frame.add(resetButton);
    }

    private void gridButtonPressed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("")) {
            if (currentPlayer == 1) {
                button.setText("X");
                currentPlayer = 2;
                updateCurrentPlayerLabel();
                checkForWin();
            }
            else if (currentPlayer == 2) {
                button.setText("O");
                currentPlayer = 1;
                updateCurrentPlayerLabel();
                checkForWin();
            }
        }
    }

    private void updateCurrentPlayerLabel() {
        currentPlayerLabel.setText("Current Player: " + currentPlayer);
    }

    private boolean checkWinForPlayer(int player) {
        String playerString;
        if (player == 1) {playerString = "X";}
        else {playerString = "O";}

        if (
                buttons[0][0].getText().equals(playerString) &&
                buttons[0][1].getText().equals(playerString) &&
                buttons[0][2].getText().equals(playerString)
        ) {
            return true;
        }else if (
                buttons[1][0].getText().equals(playerString) &&
                buttons[1][1].getText().equals(playerString) &&
                buttons[1][2].getText().equals(playerString)
        ) {
            return true;
        }else if (
                buttons[2][0].getText().equals(playerString) &&
                buttons[2][1].getText().equals(playerString) &&
                buttons[2][2].getText().equals(playerString)
        ) {
            return true;
        } else if (
                buttons[0][0].getText().equals(playerString) &&
                buttons[1][1].getText().equals(playerString) &&
                buttons[2][2].getText().equals(playerString)
        ) {
            return true;
        } else if (
                buttons[2][0].getText().equals(playerString) &&
                        buttons[1][1].getText().equals(playerString) &&
                        buttons[0][2].getText().equals(playerString)
        ) {
            return true;
        } else if (
                buttons[0][0].getText().equals(playerString) &&
                        buttons[1][0].getText().equals(playerString) &&
                        buttons[2][0].getText().equals(playerString)
        ) {
            return true;
        }else if (
                buttons[0][1].getText().equals(playerString) &&
                        buttons[1][1].getText().equals(playerString) &&
                        buttons[2][1].getText().equals(playerString)
        ) {
            return true;
        } else if (
                buttons[0][2].getText().equals(playerString) &&
                        buttons[1][2].getText().equals(playerString) &&
                        buttons[2][2].getText().equals(playerString)
        ) {
            return true;
        }

        return false;
    }

    private void stopGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void clearBoard() {
        for (JButton[] row: buttons) {
            for (JButton button: row) {
                button.setEnabled(true);
                button.setText("");
            }
        }
    }

    private void restartGame(ActionEvent e) {
        clearBoard();
        winnerLabel.setText("Winner: ");
        if (startingPlayer == 1) {
            startingPlayer = 2;
            currentPlayer = 2;
        } else {
            startingPlayer = 1;
            currentPlayer = 1;
        }
        updateCurrentPlayerLabel();
    }

    private boolean checkFullBoard() {
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) { isFull = false; break;}
            }
        }
        return isFull;
    }

    private boolean checkForWin() {
        if (checkFullBoard()) {
            winnerLabel.setText("Winner: None");
            stopGame();
            return true;
        }
        else if (checkWinForPlayer(1)) {
            winnerLabel.setText("Winner: 1");
            stopGame();
            return true;
        }else if (checkWinForPlayer(2)) {
            winnerLabel.setText("Winner: 2");
            stopGame();
            return true;
        }
        return false;
    }
}

