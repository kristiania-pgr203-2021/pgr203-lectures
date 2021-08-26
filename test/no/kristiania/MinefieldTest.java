package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinefieldTest {
    
    @Test
    void shouldEmptyField() {
        String[] field = { "." };
        String[] expected = { "0" };
        assertArrayEquals(expected, new Minesweeper(field).getHints());
    }

    @Test
    void shouldShowTallMinefields() {
        String[] field = { ".", ".", "."};
        String[] expected = { "0", "0", "0"};
        assertArrayEquals(expected, new Minesweeper(field).getHints());
    }
    
    
    @Test
    void shouldShowWideMinefields() {
        String[] field = { "....", "....", "...."};
        String[] expected = { "0000", "0000", "0000"};
        assertArrayEquals(expected, new Minesweeper(field).getHints());
    }
    
    @Test
    void shouldShowFullMinefields() {
        String[] field = { "****", "****", "****"};
        String[] expected = { "****", "****", "****"};
        assertArrayEquals(expected, new Minesweeper(field).getHints());
    }

    @Test
    void shouldShowHintOnSameRowAsMine() {
        String[] field = { "..*.."};
        String[] expected = { "01*10" };
        assertArrayEquals(expected, new Minesweeper(field).getHints());
    }

    @Test
    void shouldShowHintOnSameColumnAsMine() {
        String[] field = { ".",".","*",".","."};
        String[] expected = { "0","1","*","1","0" };
        assertArrayEquals(expected, new Minesweeper(field).getHints());
    }
    
    
    
    
    
}
