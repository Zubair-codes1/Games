package games.Sudoku;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Sudoku {

    private int boardWidth = 600;
    private int boardHeight = 650;

    private JFrame frame;

    private int[][] puzzle = {
            {0, 0, 7, 4, 9, 1, 6, 0 ,5},
            {2, 0, 0, 0, 6, 0, 3, 0, 9},
            {0, 0, 0, 0, 0, 7, 0, 1, 0},
            {0, 5, 8, 6, 0, 0, 0, 0, 4},
            {0, 0, 3, 0, 0, 0, 0, 9, 0},
            {0, 0, 6, 2, 0, 0, 1, 8, 7},
            {9, 0, 4, 0, 7, 0, 0, 0, 2},
            {6, 7, 0, 8, 3, 0, 0, 0, 0},
            {8, 1, 0, 0, 4, 5, 0, 0, 0}
    };

    private int[][] solution = {
        {3, 8, 7, 4, 9, 1, 6, 2 ,5},
        {2, 4, 1, 5, 6, 8, 3, 7, 9},
        {5, 6, 9, 3, 2, 7, 4, 1, 8},
        {7, 5, 8, 6, 1, 9, 2, 3, 4},
        {1, 2, 3, 7, 8, 4, 5, 9, 6},
        {4, 9, 6, 2, 5, 3, 1, 8, 7},
        {9, 3, 4, 1, 7, 6, 8, 5, 2},
        {6, 7, 5, 8, 3, 2, 9, 4, 1},
        {8, 1, 2, 9, 4, 5, 7, 6, 3}
    };

    private JLabel textLabel;
    private JPanel textPanel;
    private JPanel boardPanel;

    public Sudoku() {
        frame = new JFrame("Sudoku");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);


        textLabel = new JLabel();
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Sudoku: 0");

        textPanel = new JPanel();
        textPanel.add(textLabel);

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));
        setupTiles();


    }

    private void setupTiles() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Tile tile = new Tile(row, col);
                int tileInt = puzzle[row][col];
                tile.setText(Integer.toString(tileInt));
                tile.setFocusable(false);
                boardPanel.add(tile);
            }
        }
    }
}
