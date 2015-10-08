package com.barrypress.dd.core.board;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.barrypress.dd.core.character.PC;
import net.dermetfan.gdx.maps.MapUtils;

import java.util.Comparator;
import java.util.List;

public class BoardView extends ApplicationAdapter implements InputProcessor {

    final Comparator<PC> spriteComparator = new Comparator<PC>() {
        @Override
        public int compare(PC o1, PC o2) {
            if (o1.getCellY() > o2.getCellY()) {
                return -1;
            }
            if (o1.getCellY() < o2.getCellY()) {
                return 1;
            }
            // Same Y value
            if (o1.getCellX() > o2.getCellX()) {
                return 1;
            }
            if (o1.getCellX() < o2.getCellX()) {
                return -1;
            }
            return 0;
        }
    };

    private float viewHeight;
    private float viewWidth;

    private BoardTile tiles;
    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private List<PC> characters;

    public BoardView(List<PC> characters) {
        this.characters = characters;
    }

    public void init() {

        viewHeight = (float) Gdx.graphics.getHeight() * .69f;
        viewWidth = (float) Gdx.graphics.getWidth() * .79f;

        tiles = new BoardTile();

        map = new TiledMap();
        MapLayers layers = map.getLayers();

        TiledMapTileLayer layer = new TiledMapTileLayer(100, 100, 64, 32);

        layer.setName("tiles");
        layers.add(tiles.createStartTile(layer));
        tiles.createTile((TiledMapTileLayer) layers.get("tiles"), 1, 0, 8, BoardTile.ROTATION.ROTATE_0);
        tiles.createTile((TiledMapTileLayer) layers.get("tiles"), 2, 0, 12, BoardTile.ROTATION.ROTATE_0);

        characters.get(0).getSprite().setPosition(32f + characters.get(0).getOffsetX(), 16f);
        characters.get(0).getHighlightSprite().setPosition(32f + characters.get(0).getOffsetX(), 16f);
        characters.get(0).setCellX(0);
        characters.get(0).setCellY(1);
        characters.get(1).getSprite().setPosition(64f + characters.get(1).getOffsetX(), 32f);
        characters.get(1).getHighlightSprite().setPosition(64f + characters.get(1).getOffsetX(), 32f);
        characters.get(1).setCellX(0);
        characters.get(1).setCellY(2);
        characters.get(2).getSprite().setPosition(128f + characters.get(2).getOffsetX(), 32f);
        characters.get(2).getHighlightSprite().setPosition(128f + characters.get(2).getOffsetX(), 32f);
        characters.get(2).setCellX(1);
        characters.get(2).setCellY(3);
        characters.get(3).getSprite().setPosition(160f + characters.get(3).getOffsetX(), 16f);
        characters.get(3).getHighlightSprite().setPosition(160f + characters.get(3).getOffsetX(), 16f);
        characters.get(3).setCellX(2);
        characters.get(3).setCellY(3);

        renderer = new IsometricTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewWidth, viewHeight);
        camera.translate(0 - viewWidth * .25f, 0 - 64f);
    }

    public void render() {
        camera.update();

        Gdx.gl20.glClearColor(47 / 255f, 47 / 255f, 47 / 255f, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, Math.round(Gdx.graphics.getHeight() * .34f), Math.round(viewWidth), Math.round(viewHeight));

        characters.sort(spriteComparator);

        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.getBatch().begin();

        for (PC pc : characters) {
            pc.getDrawSprite().draw(renderer.getBatch());
        }

        renderer.getBatch().end();
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom += 0.1 * amount;
        // Don't go below the map
        if (camera.zoom < 0.01)
            camera.zoom = (float) 0.01;
        return false;
    }

    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.position.add(camera.zoom * -deltaX, camera.zoom * deltaY, 0);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = camera.unproject(clickCoordinates, 0, Math.round(Gdx.graphics.getHeight() * .34f), Math.round(viewWidth), Math.round(viewHeight));
        Vector3 isoSpot = MapUtils.toIsometricGridPoint(position, 64f, 32f);

        int x = (int) isoSpot.x;
        int y = (int) isoSpot.y;

        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.touchDown);
        tiles.clearHighlightTiles((TiledMapTileLayer) map.getLayers().get("tiles"));
        for (PC pc : characters) {
            pc.setHighlighted(false);
            if (pc.getCellX() == x && pc.getCellY() == y) {
                pc.setHighlighted(true);
                pc.getListener().clicked(inputEvent, x, y);
                tiles.highlightTiles((TiledMapTileLayer) map.getLayers().get("tiles"), x, y, 3);
            }
        }

/*
        float x1 = (32 * x) + (32 * y);
        float y1 = (16 * y) - (16 * x);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tiles");
        if (layer.getCell(x, y) != null) {
            if (layer.getCell(x, y).getTile() != null) {
                Boolean wall = (Boolean) layer.getCell(x, y).getTile().getProperties().get("target");

                if (wall != null && !wall) {
                    characters.get(3).getSprite().setPosition(x1 + characters.get(3).getOffsetX(), y1 + characters.get(3).getOffsetY());
                    //characters.get(0).getHighlightSprite().setPosition(x1, y1);
                }
            }
        }
        */
        return true;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A)
           camera.translate(-32, 0);
        if (keycode == Input.Keys.D)
           camera.translate(32, 0);
        if (keycode == Input.Keys.W)
           camera.translate(0, -32);
        if (keycode == Input.Keys.S)
           camera.translate(0, 32);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

}
