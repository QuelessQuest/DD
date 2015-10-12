package com.barrypress.dd.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.barrypress.dd.core.board.BoardTile;
import com.barrypress.dd.core.utility.DDUtils;

import java.util.List;

public abstract class Piece {

    private Integer ac;
    private Integer hp;
    private int cellX;
    private int cellY;
    private int tileX;
    private int tileY;
    private float offsetX;
    private float offsetY;
    private String name;
    private boolean highlighted;

    private PieceListener listener;
    private Sprite sprite;
    private Sprite highlightSprite;

    public void init() {
        offsetX = 0f;
        offsetY = 0f;
        highlighted = false;
    }

    // TODO - Is it a valid cell??
    public void move(TiledMap map, Piece piece, List<Piece> pieces) {

        int x = piece.getCellX();
        int y = piece.getCellY();

        for (int x1 = x - 1; x1 <= x + 1; x1++) {
            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                if (x1 == x && y1 == y) continue;
                if (DDUtils.isAvailable(pieces, x1, y1)) {
                    setCellX(x1);
                    setCellY(y1);
                    return;
                }
            }
        }
    }

    public Piece getNearestOnTile(List<? extends Piece> pieces) {
        return DDUtils.getNearestOnTile(getTileX(), getTileY(), getCellX(), getCellY(), pieces);
    }

    public Piece getNearestWithinXTiles(int distance, List<? extends Piece> pieces) {
        return DDUtils.getNearestWithinXTiles(distance, getTileX(), getTileY(), pieces);
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

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getHighlightSprite() {
        return highlightSprite;
    }

    public void setHighlightSprite(Sprite highlightSprite) {
        this.highlightSprite = highlightSprite;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public Sprite getDrawSprite() {
        return highlighted ? highlightSprite : sprite;
    }

    public PieceListener getListener() {
        return listener;
    }

    public void setListener(PieceListener listener) {
        this.listener = listener;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }
}
