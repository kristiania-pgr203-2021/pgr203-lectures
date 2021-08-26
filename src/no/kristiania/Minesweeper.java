package no.kristiania;

public class Minesweeper {
    private final String[] field;

    public Minesweeper(String[] field) {
        this.field = field;
    }

    public String[] getHints() {
        String[] hints = new String[field.length];
        for (int row = 0; row < field.length; row++) {
            String rowHint = "";
            for (int column = 0; column < this.field[row].length(); column++) {
                if (hasMine(row, column)) {
                    rowHint += "*";
                } else {
                    rowHint += countNeighbourMines(row, column);
                }
            }
            hints[row] = rowHint;
        }
        return hints;
    }

    private int countNeighbourMines(int i, int j) {
        int neighbourMines = 0;
        if (hasMine(i, j - 1)) {
            neighbourMines = 1;
        } else if (hasMine(i, j + 1)) {
            neighbourMines = 1;
        } else if (hasMine(i -1, j)) {
            neighbourMines = 1;
        } else if (hasMine(i + 1, j)) {
            neighbourMines = 1;
        }
        return neighbourMines;
    }

    private boolean hasMine(int row, int column) {
        if (row < 0 || this.field.length <= row) {
            return false;
        }
        
        if (column < 0 || this.field[row].length() <= column) {
            return false;
        }
        
        return this.field[row].charAt(column) == '*';
    }
}
