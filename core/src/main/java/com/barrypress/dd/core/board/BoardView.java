package com.barrypress.dd.core.board;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.utility.DDIsometricTiledMapRenderer;
import net.dermetfan.gdx.maps.MapUtils;

import java.util.List;

public class BoardView extends ApplicationAdapter implements InputProcessor {

    private float viewHeight;
    private float viewWidth;

    private BoardTile tiles;
    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private TextureAtlas spriteSheet;
    private OrthographicCamera camera;

    private List<PC> characters;

    public BoardView(List<PC> characters, TextureAtlas spriteSheet) {
        this.spriteSheet = spriteSheet;
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

        characters.get(0).getSprite().setPosition(32f, 16f);
        characters.get(1).getSprite().setPosition(64f, 32f);

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

        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.getBatch().begin();
        if (characters.get(0).getHighlighted()) {
            characters.get(0).getHighlightSprite().draw(renderer.getBatch());
        } else {
            characters.get(0).getSprite().draw(renderer.getBatch());
        }
        characters.get(1).getSprite().draw(renderer.getBatch());

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

        float x1 = (32 * x) + (32 * y);
        float y1 = (16 * y) - (16 * x);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tiles");
        if (layer.getCell(x, y) != null) {
            if (layer.getCell(x, y).getTile() != null) {
                Boolean wall = (Boolean) layer.getCell(x, y).getTile().getProperties().get("target");

                if (wall != null && !wall) {
                    characters.get(1).getSprite().setPosition(x1 + characters.get(1).getOffsetX(), y1 + characters.get(1).getOffsetY());
                    //characters.get(0).getHighlightSprite().setPosition(x1, y1);
                }
            }
        }
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
