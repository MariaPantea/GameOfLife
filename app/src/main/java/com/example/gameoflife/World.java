package com.example.gameoflife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class World {

    private static final Random RANDOM = new Random();
    private int width, height;
    private Cell[][] board;

    World(int width, int height) {
        this.width = width;
        this.height = height;

        board = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Cell(i, j, RANDOM.nextBoolean());
            }
        }

    }


    Cell get(int i, int j) {
        return board[i][j];
    }

    private int countNeighbours(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i != x || j != y) && i >= 0 && i < width && j >= 0 && j < height) {
                    Cell cell = board[i][j];
                    if (cell.alive) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /*
    Conway's Game of Life is based on 4 rules:
     1. Any live cell with fewer than two live neighbors dies, as if caused by underpopulation.
     2. Any live cell with two or three live neighbors lives on to the next generation.
     3. Any live cell with more than three live neighbors dies, as if by overpopulation.
     4.Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     */
    void nextGeneration() {
        List<Cell> liveCells = new ArrayList<>();
        List<Cell> deadCells = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = board[i][j];
                int neighbours = countNeighbours(cell.x, cell.y);

                // rule 1
                if (cell.alive && neighbours < 2) {
                    deadCells.add(cell);
                }
                // rule 2
                else if (cell.alive && (neighbours == 2 || neighbours == 3)) {
                    liveCells.add(cell);
                }
                // rule 4
                else if (!cell.alive && neighbours == 3) {
                    liveCells.add(cell);
                } else {
                    deadCells.add(cell);
                }
            }
        }
        // update future live and dead cells
        for (Cell cell : liveCells) {
            cell.reborn();
        }
        for (Cell cell : deadCells) {
            cell.die();
        }
    }


}
