package no.kristiania.pgr301.minesweeper;

public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] result = new String[input.length];
        for (int i = 0, inputLength = input.length; i < inputLength; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < input[i].length(); j++) {
                if (input[i].charAt(j) == '*') {
                    line.append("*");
                } else {
                    line.append("0");
                }
            }
            result[i] = line.toString();
        }
        return result;
    }
}
