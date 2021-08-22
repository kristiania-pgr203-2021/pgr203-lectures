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
                } else if (hasMine(row, col+1)) {
                    line.append(1);
                } else {
                    line.append(0);
                }
            }
            result[row] = line.toString();
        }
        return result;
    }

    private boolean hasMine(int row, int col) {
        return col < input[row].length() && input[row].charAt(col) == '*';
    }
}
