package com.barrypress.dd.core.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.barrypress.dd.core.SharedAssets;

public class MenuListener extends ClickListener {

    public enum ButtonType {NEW, LOAD}

    private ButtonType buttonType;
    private SharedAssets sharedAssets;

    public MenuListener(ButtonType type, SharedAssets sharedAssets) {
        buttonType = type;
        this.sharedAssets = sharedAssets;
    }

    @Override
    public void clicked (InputEvent event, float x, float y) {
        if (buttonType == ButtonType.NEW) {
            System.out.println("NEW BUTTON CLICKED");
            sharedAssets.getScreen().dispose();
            Gdx.gl20.glClearColor(0, 0, 0, 1);
            Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
            sharedAssets.getInputMultiplexer().addProcessor(sharedAssets.getHudView().getStage());
            sharedAssets.getInputMultiplexer().addProcessor(sharedAssets.getBoardView());
            sharedAssets.setBoardViewVisible(true);
            sharedAssets.setScreen(sharedAssets.getHudView());
        }
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
