package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinefieldTest {
    
    @Test
    void shouldEmptyField() {
        String[] field = { "." };
        String[] expected = { "0" };
        assertArrayEquals(new Minesweeper(field).getHints(), expected);
    }

    @Test
    void shouldShowTallMinefields() {
        String[] field = { ".", ".", "."};
        String[] expected = { "0", "0", "0"};
        assertArrayEquals(new Minesweeper(field).getHints(), expected);
    }
    
    
    @Test
    void shouldShowWideMinefields() {
        String[] field = { "....", "....", "...."};
        String[] expected = { "0000", "0000", "0000"};
        assertArrayEquals(new Minesweeper(field).getHints(), expected);
    }
    
    
}
