package games.Sudoku;

import java.util.*;

public class Board {

    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    private final int[][] board = new int[SIZE][SIZE];     // puzzle
    private int[][] solution;                              // full solution
    private final Random random = new Random();

    public int[][] generatePuzzle(int clues) {
        clearBoard();
        fillBoard();              // generate full solution
        solution = copyBoard(board);
        removeNumbers(clues);     // turn it into a puzzle
        return copyBoard(board);
    }

    public int[][] getSolution() {
        return copyBoard(solution);
    }

    public boolean solve() {
        return solve(board);
    }

    public int[][] getBoard() {
        return copyBoard(board);
    }

    private boolean solve(int[][] bo) {
        int[] find = findEmpty(bo);
        if (find == null) {
            return true;
        }

        int row = find[0];
        int col = find[1];

        for (int num = 1; num <= 9; num++) {
            if (isValid(bo, num, row, col)) {
                bo[row][col] = num;

                if (solve(bo)) {
                    return true;
                }

                bo[row][col] = EMPTY;
            }
        }
        return false;
    }


    private boolean fillBoard() {
        int[] find = findEmpty(board);
        if (find == null) {
            return true;
        }

        int row = find[0];
        int col = find[1];

        List<Integer> nums = shuffledNumbers();

        for (int num : nums) {
            if (isValid(board, num, row, col)) {
                board[row][col] = num;
                if (fillBoard()) {
                    return true;
                }
                board[row][col] = EMPTY;
            }
        }
        return false;
    }

    public boolean isValid(int[][] bo, int num, int row, int col) {

        // Row
        for (int i = 0; i < SIZE; i++) {
            if (bo[row][i] == num && i != col) return false;
        }

        // Column
        for (int i = 0; i < SIZE; i++) {
            if (bo[i][col] == num && i != row) return false;
        }

        // Box
        int boxX = col / 3;
        int boxY = row / 3;

        for (int i = boxY * 3; i < boxY * 3 + 3; i++) {
            for (int j = boxX * 3; j < boxX * 3 + 3; j++) {
                if (bo[i][j] == num && (i != row || j != col)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void removeNumbers(int clues) {
        int toRemove = SIZE * SIZE - clues;

        while (toRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (board[row][col] != EMPTY) {
                board[row][col] = EMPTY;
                toRemove--;
            }
        }
    }

    private int[] findEmpty(int[][] bo) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (bo[i][j] == EMPTY) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private List<Integer> shuffledNumbers() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) nums.add(i);
        Collections.shuffle(nums, random);
        return nums;
    }

    private void clearBoard() {
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(board[i], EMPTY);
        }
    }

    private int[][] copyBoard(int[][] src) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(src[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }


    public static void main(String[] args) {
        Board board = new Board();

        int[][] puzzle = board.generatePuzzle(32); // medium
        System.out.println("PUZZLE:");
        printBoard(puzzle);

        board.solve();
        System.out.println("\nSOLVED:");
        printBoard(board.getBoard());

        System.out.println("\nORIGINAL SOLUTION:");
        printBoard(board.getSolution());
    }

    private static void printBoard(int[][] bo) {
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0)
                System.out.println("------+-------+------");

            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0 && j != 0)
                    System.out.print("| ");

                System.out.print(bo[i][j] == 0 ? ". " : bo[i][j] + " ");
            }
            System.out.println();
        }
    }
}

