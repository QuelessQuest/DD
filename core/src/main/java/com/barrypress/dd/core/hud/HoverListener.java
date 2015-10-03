package com.barrypress.dd.core.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.barrypress.dd.core.character.PC;

public class HoverListener extends ClickListener {

    private PC pc;
    private Label name;
    private Label ac;
    private Label hp;
    private Label spd;
    private Label srg;

    public HoverListener(PC pc, Label name, Label ac, Label hp, Label spd, Label srg) {
        this.pc = pc;
        this.name = name;
        this.ac = ac;
        this.hp = hp;
        this.spd = spd;
        this.srg = srg;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        name.setText(pc.getName());
        ac.setText(pc.getAc().toString());
        hp.setText(pc.getHp().toString());
        spd.setText(pc.getSpeed().toString());
        srg.setText(pc.getSurge().toString());
    }
}
