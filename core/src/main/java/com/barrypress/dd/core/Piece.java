package com.barrypress.dd.core;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Piece {

    private Integer ac;
    private Integer hp;
    private int cellX;
    private int cellY;
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
}
