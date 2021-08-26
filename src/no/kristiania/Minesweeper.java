package no.kristiania;

public class Minesweeper {
    private final String[] field;

    public Minesweeper(String[] field) {
        this.field = field;
    }

    public String[] getHints() {
        String[] hints = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            hints[i] = "0";
        }
        return hints;
    }
}
