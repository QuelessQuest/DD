package com.barrypress.dd.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.barrypress.dd.core.board.BoardView;
import com.barrypress.dd.core.character.*;
import com.barrypress.dd.core.hud.HUDView;

import java.util.ArrayList;
import java.util.List;

public class DD implements ApplicationListener {

    private BoardView boardView;
	private HUDView hudView;
    private List<PC> characters;

	@Override
	public void create () {
        characters = new ArrayList<>();

        Thorgrim thorgrim = new Thorgrim();
        Allisa allisa = new Allisa();
        Immeril immeril = new Immeril();
        Kat kat = new Kat();

        characters.add(thorgrim);
        characters.add(allisa);
        characters.add(immeril);
        characters.add(kat);

        boardView = new BoardView();
        boardView.init();

        hudView = new HUDView(characters);
        hudView.init();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hudView.getStage());
        inputMultiplexer.addProcessor(boardView.getStage());

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
