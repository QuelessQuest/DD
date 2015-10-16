package com.barrypress.dd.core.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class FlatScreen {

    private Stage stage;

    public abstract void init();

    public abstract void render();

    public abstract void dispose();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
