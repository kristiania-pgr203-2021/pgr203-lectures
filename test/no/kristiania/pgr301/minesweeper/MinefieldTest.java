package no.kristiania.pgr301.minesweeper;

public class MinefieldTest {
    
    @Test
    void shouldShowsEmptyMinefield() {
        String[] input = { "." };
        String[] expected = { "0" };
        assertEquals(expected, new Minefield(input).getHints());
    }
    
}
