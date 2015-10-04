package com.barrypress.dd.core.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BoardView {

    private float viewHeight;
    private float viewWidth;

    private Stage stage;
    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public BoardView() {
    }

    public void init() {

        viewHeight = (float) Gdx.graphics.getHeight() * .69f;
        viewWidth = (float) Gdx.graphics.getWidth() * .79f;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);

        BoardTile tiles = new BoardTile();

        map = new TiledMap();
        MapLayers layers = map.getLayers();
        layers.add(tiles.create(1, 0, 0, BoardTile.ROTATION.ROTATE_0));
        layers.add(tiles.create(2, 0, 4, BoardTile.ROTATION.ROTATE_270));

        renderer = new IsometricTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewWidth, viewHeight);
    }

    public void render() {
        Gdx.gl20.glClearColor(47/255f, 47/255f, 47/255f, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, Math.round(Gdx.graphics.getHeight() * .34f), Math.round(viewWidth), Math.round(viewHeight));

        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

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

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.MIDDLE)
            camera.zoom = 1;
        return false;
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public Stage getStage() { return stage; }
}
