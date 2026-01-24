package games.Sudoku;

import javax.swing.*;

public class Tile extends JButton {
    int row;
    int col;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
