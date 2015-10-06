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
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.barrypress.dd.core.character.PC;

import java.util.List;

public class BoardView extends ApplicationAdapter implements InputProcessor {

    private float viewHeight;
    private float viewWidth;

    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private SpriteBatch batch;
    private TextureAtlas spriteSheet;
    private Matrix4 isoTransform;
    private Matrix4 invIsoTransform;
    private OrthographicCamera camera;

    private List<PC> characters;

    public BoardView(List<PC> characters, TextureAtlas spriteSheet) {
        this.spriteSheet = spriteSheet;
        this.characters = characters;
    }

    public void init() {

        viewHeight = (float) Gdx.graphics.getHeight() * .69f;
        viewWidth = (float) Gdx.graphics.getWidth() * .79f;

        batch = new SpriteBatch();

        isoTransform = new Matrix4();
        isoTransform.idt();
        isoTransform.translate(0.0f, 0.25f, 0.0f);
        isoTransform.scale((float)(Math.sqrt(2.0) / 2.0), (float)(Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);

        invIsoTransform = new Matrix4(isoTransform);
        invIsoTransform.inv();

        //stage = new Stage(new ScreenViewport(), batch);

        BoardTile tiles = new BoardTile();

        map = new TiledMap();
        MapLayers layers = map.getLayers();
        layers.add(tiles.create(2, 0, 4, BoardTile.ROTATION.ROTATE_0));
        layers.add(tiles.create(1, 0, 0, BoardTile.ROTATION.ROTATE_0));

        renderer = new IsometricTiledMapRenderer(map, batch);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewWidth, viewHeight);
        camera.translate(0 - viewWidth * .25f, 0 - 64f);
    }

    public void render() {
        Gdx.gl20.glClearColor(47/255f, 47/255f, 47/255f, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, Math.round(Gdx.graphics.getHeight() * .34f), Math.round(viewWidth), Math.round(viewHeight));

        camera.update();
        renderer.setView(camera);
        renderer.render();
        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.getBatch().begin();
        characters.get(0).getSprite().draw(renderer.getBatch());
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
        Vector3 position = camera.unproject(clickCoordinates);
        position.mul(isoTransform);
        Vector3 newPosition = camera.project(position);
        //characters.get(0).getSprite().setPosition(clickCoordinates.x, clickCoordinates.y);
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
        if(keycode == Input.Keys.A)
            camera.translate(-32,0);
        if(keycode == Input.Keys.D)
            camera.translate(32,0);
        if(keycode == Input.Keys.W)
            camera.translate(0,-32);
        if(keycode == Input.Keys.S)
            camera.translate(0,32);
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
