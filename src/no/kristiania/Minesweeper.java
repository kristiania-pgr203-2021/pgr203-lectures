package no.kristiania;

public class Minesweeper {
    private final String[] field;

    public Minesweeper(String[] field) {
        this.field = field;
    }

    public String[] getHints() {
        String[] hints = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            String rowHint = "";
            for (int j = 0; j < this.field[i].length(); j++) {
                if (hasMine(i, j)) {
                    rowHint += "*";
                } else if(hasMine(i, j+1)){
                    rowHint += "1";
                } 
                else {
                    rowHint += "0";
                }
            }
            hints[i] = rowHint;
        }
        return hints;
    }

    private boolean hasMine(int row, int column) {
        if(this.field[row].length() <= column){
            return false;
        }
        return this.field[row].charAt(column) == '*';
    }
}
