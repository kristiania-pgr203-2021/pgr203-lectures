package no.kristiania.pgr301.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinefieldTest {
    
    @Test
    void shouldShowsEmptyMinefield() {
        String[] input = { "." };
        String[] expected = { "0" };
        assertEquals(expected, new Minefield(input).getHints());
    }
    
}
