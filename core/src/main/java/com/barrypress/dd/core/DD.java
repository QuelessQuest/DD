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
import com.barrypress.dd.core.monster.RatSwarm;

public class DD implements ApplicationListener {

    private BoardView boardView;
	private HUDView hudView;

	@Override
	public void create () {

        AssetManager assetManager = new AssetManager();
        assetManager.load("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt", TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas spriteSheet = assetManager.get("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt");
        Skin skin = new Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/uiskin.json"));
        Skin smallSkin = new Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/small.json"));
        SharedAssets sharedAssets = new SharedAssets(skin, smallSkin);

        Thorgrim thorgrim = new Thorgrim(spriteSheet, skin);
        Allisa allisa = new Allisa(spriteSheet, skin);
        Immeril immeril = new Immeril(spriteSheet, skin);
        Kat kat = new Kat(spriteSheet, skin);

        RatSwarm ratSwarm = new RatSwarm(spriteSheet);
        sharedAssets.getMonsters().add(ratSwarm);

        sharedAssets.getCharacters().add(thorgrim);
        sharedAssets.getCharacters().add(immeril);
        sharedAssets.getCharacters().add(allisa);
        sharedAssets.getCharacters().add(kat);

        boardView = new BoardView(sharedAssets);
        boardView.init();

        hudView = new HUDView(sharedAssets, assetManager, spriteSheet, skin);
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
