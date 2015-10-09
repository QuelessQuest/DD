package com.barrypress.dd.core;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.barrypress.dd.core.character.PC;

import java.util.List;

public abstract class PieceListener extends ClickListener {

    private List<? extends Piece> objects;
    private Piece piece;
    private Label name;
    private Label ac;
    private Label hp;

    public PieceListener() {}

    public void init(List<? extends Piece> objects, Piece piece, Label name, Label ac, Label hp) {
        this.objects = objects;
        this.piece = piece;
        this.name = name;
        this.ac = ac;
        this.hp = hp;
    }

    @Override
    public abstract void clicked (InputEvent event, float x, float y);

    @Override
    public abstract void enter(InputEvent event, float x, float y, int pointer, Actor fromActor);

    @Override
    public abstract void exit(InputEvent event, float x, float y, int pointer, Actor toActor);

    public List<? extends Piece> getObjects() {
        return objects;
    }

    public void setObjects(List<Piece> objects) {
        this.objects = objects;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Label getAc() {
        return ac;
    }

    public void setAc(Label ac) {
        this.ac = ac;
    }

    public Label getHp() {
        return hp;
    }

    public void setHp(Label hp) {
        this.hp = hp;
    }
}
