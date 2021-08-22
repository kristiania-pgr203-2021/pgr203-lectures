package no.kristiania.pgr301.minesweeper;

public class Minefield {
    private final String[] input;

    public Minefield(String[] input) {
        this.input = input;
    }

    public String[] getHints() {
        String[] result = new String[input.length];
        for (int i = 0, inputLength = input.length; i < inputLength; i++) {
            result[i] = "0";
        }
        return result;
    }
}
