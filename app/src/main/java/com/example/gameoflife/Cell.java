package com.example.gameoflife;

class Cell {

    int x;
    int y;
    boolean alive;

    Cell(int x, int y, boolean alive) {
        this.x = x;
        this.y = y;
        this.alive = alive;
    }

    void die() {
        alive = false;
    }

    void reborn() {
        alive = true;
    }
}
