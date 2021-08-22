package no.kristiania.pgr301.minesweeper;

/**
 * Represents a minefield for the game Mine Sweeper. The board is a
 * rectagonal grid of cells that either have a mine on it or not.
 * For cells that don't have a mine, the minefield calculates a hint
 * with the number of neighbouring cells that have mines on them
 */
public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] result = new String[input.length];
        // Go through each row and column and calculate what value should be in the cell
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
        } else {
            return String.valueOf(countMinesAroundCell(row, col));
        }
    }

    private int countMinesAroundCell(int row, int col) {
        int neighbourMines = 0;
        // Count number of cells with mines from row - 1 to row + 1 and col - 1 to col +1
        for (int r = row -1; r <= row +1; r++) {
            for (int c = col -1; c <= col +1; c++) {
                if (hasMine(r, c)) {
                    neighbourMines += 1;
                }
            }
        }
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
