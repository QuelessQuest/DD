package com.barrypress.dd.core.monster;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.PieceListener;
import com.barrypress.dd.core.character.PC;

import java.util.List;

public class MonsterListener extends PieceListener {

    public MonsterListener() {}

    @Override
    public void clicked (InputEvent event, float x, float y) {
        for (Piece piece : getObjects()) {
            piece.setHighlighted(false);
        }
        Monster monster = (Monster) getPiece();
        getName().setText(monster.getName());
        getAc().setText(monster.getAc().toString());
        getHp().setText(monster.getHp().toString());
        monster.setHighlighted(true);
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
