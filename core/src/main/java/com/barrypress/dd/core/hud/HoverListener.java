package com.barrypress.dd.core.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.barrypress.dd.core.character.PC;

public class HoverListener extends ClickListener {

    private PC pc;
    private Label name;

    public HoverListener(PC pc, Label name) {
        this.pc = pc;
        this.name = name;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        name.setText(pc.getName());
    }
}
