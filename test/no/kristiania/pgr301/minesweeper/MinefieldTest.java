package no.kristiania.pgr301.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MinefieldTest {

    @Test
    void shouldShowsEmptyMinefield() {
        assertArrayEquals(new String[]{"0"}, new Minefield(new String[]{"."}).getHints());
    }

    @Test
    void hintsShouldReflectInputBoardSize() {
        assertArrayEquals(
                new String[]{"00", "00", "00"},
                new Minefield(new String[]{"..", "..", ".."}).getHints()
        );
    }

    @Test
    void shouldDisplayMines() {
        assertArrayEquals(
                new String[]{"****", "****"},
                new Minefield(new String[]{"****", "****"}).getHints()
        );
    }

    @Test
    void shouldDisplayHintOnSameRowAsMine() {
        assertArrayEquals(
                new String[]{"01*10"},
                new Minefield(new String[]{"..*.."}).getHints()
        );
    }

    @Test
    void shouldDisplayHintOnSameColumnAsMine() {
        assertArrayEquals(
                new String[]{"0", "1", "*", "1", "0"},
                new Minefield(new String[]{".", ".", "*", ".", "."}).getHints()
        );
    }

    @Test
    void shouldDisplayHintAroundMine() {
        assertArrayEquals(
                new String[]{"111", "1*1", "111"},
                new Minefield(new String[]{"...", ".*.", "..."}).getHints()
        );
    }

    @Test
    void shouldCountMinesAroundCell() {
        assertArrayEquals(
                new String[]{"***", "*8*", "***"},
                new Minefield(new String[]{"***", "*.*", "***"}).getHints()
        );
    }

    @Test
    void shouldDisplayMinefield() {
        String[] expected = {
                "00000",
                "11000",
                "*2100",
                "2*210",
                "12*10",
        };
        String[] input = {
                ".....",
                ".....",
                "*....",
                ".*...",
                "..*..",
        };
        assertArrayEquals(expected, new Minefield(input).getHints());
    }

}
