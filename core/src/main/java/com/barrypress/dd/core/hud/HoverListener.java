package com.barrypress.dd.core.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HoverListener extends ClickListener {

    private Actor myActor;

    public HoverListener(Actor myActor) {
        this.myActor = myActor;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        //Label nameLabel = (Label) myActor.getUserObject();
        //nameLabel.setText(myActor.getName());
    }
}
