package no.kristiania.pgr301.minesweeper;

public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] result = new String[input.length];
        for (int row = 0, inputLength = input.length; row < inputLength; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < input[row].length(); col++) {
                line.append(calculateCellValue(row, col));
            }
            result[row] = line.toString();
        }
        return result;
    }

    private String calculateCellValue(int row, int col) {
        if (hasMine(row, col)) {
            return "*";
        }
        return String.valueOf(countMinesAroundCell(row, col));
    }

    private int countMinesAroundCell(int row, int col) {
        int neighbourMines = 0;
        if (hasMine(row-1, col-1)) neighbourMines++;
        if (hasMine(row-1, col)) neighbourMines++;
        if (hasMine(row-1, col+1)) neighbourMines++;
        if (hasMine(row, col-1)) neighbourMines++;
        if (hasMine(row, col+1)) neighbourMines++;
        if (hasMine(row+1, col-1)) neighbourMines++;
        if (hasMine(row+1, col)) neighbourMines++;
        if (hasMine(row+1, col+1)) neighbourMines++;
        return neighbourMines;
    }

    private boolean hasMine(int row, int col) {
        if (row < 0 || input.length <= row) {
            return false;
        }
        if (col < 0 || input[row].length() <= col) {
            return false;
        }
        return input[row].charAt(col) == '*';
    }
}
