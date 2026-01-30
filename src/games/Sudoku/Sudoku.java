package games.Sudoku;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.sound.sampled.Line;
import javax.swing.*;

public class Sudoku extends JPanel {

    private int panelWidth;
    private int panelHeight;

    private JLabel titleLabel;
    private JPanel titlePanel;
    private JPanel numbersPanel;
    private JPanel inputPanel;

    private Board board;
    private int[][] puzzle;
    private final int[][] solution;

    private int currentSelectedNumber = 0;

    public  Sudoku(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;

        this.setPreferredSize(new Dimension(this.panelWidth, this.panelHeight));
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());

        titleLabel = new JLabel("Sudoku");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        titlePanel.add(titleLabel);

        numbersPanel = new JPanel();
        numbersPanel.setBackground(Color.white);
        numbersPanel.setLayout(new GridLayout(9, 9));

        board = new Board();
        board.generatePuzzle(80);

        puzzle = board.getBoard();
        solution = board.getSolution();

        inputPanel = new JPanel();
        inputPanel.setBackground(Color.white);

        createBoard();
        createInputNumbers();

        this.add(titlePanel,BorderLayout.NORTH);
        this.add(numbersPanel,BorderLayout.CENTER);
        this.add(inputPanel,BorderLayout.SOUTH);

        
        this.setVisible(true);
    }

    private void createBoard() {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {

                JButton button = getJButton(i, j);

                int finalI = i;
                int finalJ = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        updateButtons(e, currentSelectedNumber, finalI, finalJ);
                    }
                });

                numbersPanel.add(button);
            }
        }
    }

    private void updateButtons(ActionEvent e,int currentSelectedNumber, int finalI, int finalJ) {
        JButton sourceButton = (JButton) e.getSource();

        if (currentSelectedNumber != 0 && sourceButton.getText().isEmpty()) {

            if (solution[finalI][finalJ] != currentSelectedNumber) {
                sourceButton.setBackground(Color.RED);
                sourceButton.setOpaque(true);
                Timer timer = new Timer(300, event -> {
                    sourceButton.setBackground(Color.WHITE);
                    sourceButton.setOpaque(false); // IMPORTANT
                });
                timer.setRepeats(false);
                timer.start();



            } else {
                sourceButton.setForeground(Color.BLACK);
                sourceButton.setText(String.valueOf(currentSelectedNumber));
            }
        }
    }

    private JButton getJButton(int i, int j) {
        String valueOfNumber = puzzle[i][j] + "";
        if (valueOfNumber.equals("0")) { valueOfNumber = ""; }

        JButton button = new JButton(valueOfNumber);
        button.setBackground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 50));
        button.setForeground(Color.DARK_GRAY);

        int top    = (i % 3 == 0) ? 2 : 1;
        int left   = (j % 3 == 0) ? 2 : 1;
        int bottom = (i == 8) ? 2 : 1;
        int right  = (j == 8) ? 2 : 1;

        button.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
        return button;
    }

    private void createInputNumbers() {
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton(i + "");
            button.setBackground(Color.white);
            button.setFont(new Font("Arial", Font.BOLD, 50));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentSelectedNumber = Integer.parseInt(button.getText());
                }
            });
            inputPanel.add(button);
        }
    }
}
