package com.barrypress.dd.core.board;

public class Tile {

    private int id;
    private int cellX;
    private int cellY;

    private Tile tileN;
    private Tile tileS;
    private Tile tileE;
    private Tile tileW;

    public Tile(int id) {
        this.id = id;
    }

    public Tile(int id, int cellX, int cellY) {
        this.id = id;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCellX() {
        return cellX;
    }

    public void setCellX(int cellX) {
        this.cellX = cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public void setCellY(int cellY) {
        this.cellY = cellY;
    }

    public Tile getTileN() {
        return tileN;
    }

    public void setTileN(Tile tileN) {
        this.tileN = tileN;
    }

    public Tile getTileS() {
        return tileS;
    }

    public void setTileS(Tile tileS) {
        this.tileS = tileS;
    }

    public Tile getTileE() {
        return tileE;
    }

    public void setTileE(Tile tileE) {
        this.tileE = tileE;
    }

    public Tile getTileW() {
        return tileW;
    }

    public void setTileW(Tile tileW) {
        this.tileW = tileW;
    }
}
