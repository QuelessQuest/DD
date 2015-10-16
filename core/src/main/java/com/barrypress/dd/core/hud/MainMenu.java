package com.barrypress.dd.core.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.barrypress.dd.core.SharedAssets;

public class MainMenu extends FlatScreen {

    private SharedAssets sharedAssets;
    private SpriteBatch batch;
    private TextureAtlas spriteSheet;

    public MainMenu(SharedAssets sharedAssets, TextureAtlas spriteSheet) {
        this.sharedAssets = sharedAssets;
        this.spriteSheet = spriteSheet;
    }

    public void init() {

        float height = (float) Gdx.graphics.getHeight();
        float width  = (float) Gdx.graphics.getWidth();

        batch = new SpriteBatch();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        setStage(new Stage(new StretchViewport(width, height), batch));

        SpriteDrawable buttonSprite = new SpriteDrawable(new Sprite(spriteSheet.findRegion("button")));
        Button newButton = new Button(buttonSprite, buttonSprite);
        newButton.addListener(new MenuListener(MenuListener.ButtonType.NEW, sharedAssets));

        Table buttonTable = new Table();
        buttonTable.setSkin(sharedAssets.getSkin());
        buttonTable.left().top();
        buttonTable.add(newButton);
        buttonTable.row();
        buttonTable.add("LOAD");
        buttonTable.row();
        buttonTable.add("OPTIONS");

        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.setSkin(sharedAssets.getSkin());
        menuTable.left().top();

        menuTable.add("TITLE GOES HERE").height(height * .20f).width(width);
        menuTable.row();
        menuTable.add(buttonTable).height(height * .60f).width(width);
        menuTable.row();
        menuTable.add("OTHER STUFF").height(height * .20f).width(width);

        getStage().addActor(menuTable);
    }

    public void render() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(47 / 255f, 47 / 255f, 47 / 255f, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getStage().setDebugAll(true);
        getStage().act();
        getStage().draw();
    }

    public void dispose() {
        batch.dispose();
        getStage().dispose();
    }
}
