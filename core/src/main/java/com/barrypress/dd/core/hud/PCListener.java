package com.barrypress.dd.core.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.PieceListener;
import com.barrypress.dd.core.character.PC;

import java.util.List;

public class PCListener extends PieceListener {

    private Label spd;
    private Label srg;

    public PCListener() {}

    public void init(List<PC> characters, Piece pc, Label name, Label ac, Label hp, Label spd, Label srg) {
        super.init(characters, pc, name, ac, hp);
        this.spd = spd;
        this.srg = srg;
    }

    @Override
    public void clicked (InputEvent event, float x, float y) {
        for (Piece piece : getObjects()) {
            piece.setHighlighted(false);
        }
        PC pc = (PC) getPiece();
        getName().setText(pc.getName());
        getAc().setText(pc.getAc().toString());
        getHp().setText(pc.getHp().toString());
        spd.setText(pc.getSpeed().toString());
        srg.setText(pc.getSurge().toString());
        pc.setHighlighted(true);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
       // pc.setHighlighted(true);
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        //pc.setHighlighted(false);
    }
}
