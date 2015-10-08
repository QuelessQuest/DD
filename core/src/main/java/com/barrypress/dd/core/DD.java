package com.barrypress.dd.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.barrypress.dd.core.board.BoardView;
import com.barrypress.dd.core.character.*;
import com.barrypress.dd.core.hud.HUDView;

import java.util.ArrayList;
import java.util.List;

public class DD implements ApplicationListener {

    private AssetManager assetManager;
    private BoardView boardView;
	private HUDView hudView;
    private List<PC> characters;
    private Skin skin;
    private TextureAtlas spriteSheet;

	@Override
	public void create () {
        characters = new ArrayList<>();

        assetManager = new AssetManager();
        assetManager.load("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt", TextureAtlas.class);
        assetManager.finishLoading();
        spriteSheet = assetManager.get("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt");
        skin = new com.badlogic.gdx.scenes.scene2d.ui.Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/uiskin.json"));

        Thorgrim thorgrim = new Thorgrim(spriteSheet, skin);
        Allisa allisa = new Allisa(spriteSheet, skin);
        Immeril immeril = new Immeril(spriteSheet, skin);
        Kat kat = new Kat(spriteSheet, skin);

        characters.add(thorgrim);
        characters.add(immeril);
        characters.add(allisa);
        characters.add(kat);

        boardView = new BoardView(characters);
        boardView.init();

        hudView = new HUDView(characters, assetManager, spriteSheet, skin);
        hudView.init();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hudView.getStage());
        inputMultiplexer.addProcessor(boardView);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {

        boardView.render();
        hudView.render();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
