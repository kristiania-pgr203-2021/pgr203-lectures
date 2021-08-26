package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinefieldTest {
    
    @Test
    void shouldEmptyField() {
        String[] field = { "." };
        String[] expected = { "0" };
        assertEquals(new Minesweeper(field).getHints(), expected);
    }
    
}
