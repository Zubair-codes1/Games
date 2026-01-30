package games.Sudoku;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {

        int windowWidth = 900;
        int windowHeight = 900;

        JFrame window = new JFrame("Sudoku");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(windowWidth, windowHeight);
        window.setLocationRelativeTo(null);

        Sudoku sudoku = new Sudoku(windowWidth ,windowHeight);

        window.add(sudoku);
        window.setVisible(true);
    }
}
