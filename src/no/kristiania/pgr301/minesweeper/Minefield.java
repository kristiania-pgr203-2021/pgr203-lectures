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
                if (hasMine(row, col)) {
                    line.append("*");
                } else {
                    int neighbourMines = 0;
                    for (int r = row-1; r <= row+1; r++) {
                        for (int c = col-1; c <= col+1; c++) {
                            if (hasMine(r, c)) {
                                neighbourMines += 1;
                            }
                        }
                    }
                    line.append(neighbourMines);
                }
            }
            result[row] = line.toString();
        }
        return result;
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
