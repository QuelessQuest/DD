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
import com.barrypress.dd.core.hud.MainMenu;
import com.barrypress.dd.core.monster.RatSwarm;

public class DD implements ApplicationListener {

    private AssetManager assetManager;
    private MainMenu mainMenu;
    private Skin skin;
    private Skin smallSkin;
    private TextureAtlas spriteSheet;
    private SharedAssets sharedAssets;

	@Override
	public void create () {

        assetManager = new AssetManager();
        assetManager.load("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt", TextureAtlas.class);
        assetManager.finishLoading();
        spriteSheet = assetManager.get("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt");
        skin = new Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/uiskin.json"));
        smallSkin = new Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/small.json"));
        sharedAssets = new SharedAssets(skin, smallSkin);

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

        mainMenu = new MainMenu(sharedAssets, spriteSheet);
        mainMenu.init();

        sharedAssets.setScreen(mainMenu);

        sharedAssets.setBoardView(new BoardView(sharedAssets));
        sharedAssets.getBoardView().init();

        sharedAssets.setHudView(new HUDView(sharedAssets, assetManager, spriteSheet));
        sharedAssets.getHudView().init();

        sharedAssets.setInputMultiplexer(new InputMultiplexer());
        sharedAssets.getInputMultiplexer().addProcessor(mainMenu.getStage());

        Gdx.input.setInputProcessor(sharedAssets.getInputMultiplexer());
    }

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
        if (sharedAssets.isBoardViewVisible()) sharedAssets.getBoardView().render();
        sharedAssets.getScreen().render();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
        mainMenu.dispose();
        skin.dispose();
        smallSkin.dispose();
        spriteSheet.dispose();
        assetManager.dispose();
	}
}
