package no.kristiania.pgr301.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MinefieldTest {
    
    @Test
    void shouldShowsEmptyMinefield() {
        assertArrayEquals(new String[]{ "0" }, new Minefield(new String[]{ "." }).getHints());
    }

    @Test
    void hintsShouldReflectInputBoardSize() {
        assertArrayEquals(
                new String[]{ "0", "0", "0" },
                new Minefield(new String[]{ ".", ".", "." }).getHints()
        );
    }
    
}
