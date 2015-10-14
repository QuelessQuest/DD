package com.barrypress.dd.core.board;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.SharedAssets;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.monster.Monster;
import com.barrypress.dd.core.utility.DDUtils;
import net.dermetfan.gdx.maps.MapUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardView extends ApplicationAdapter implements InputProcessor {

    final Comparator<Piece> spriteComparator = new Comparator<Piece>() {
        @Override
        public int compare(Piece o1, Piece o2) {
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
    private Map<Integer, Map<Integer, Tile>> tileInfo;
    private ShapeRenderer shapeRenderer;

    private SharedAssets sharedAssets;

    public BoardView(SharedAssets sharedAssets) {
        this.sharedAssets = sharedAssets;
    }

    public void init() {

        viewHeight = (float) Gdx.graphics.getHeight() * .69f;
        viewWidth = (float) Gdx.graphics.getWidth() * .79f;

        List<PC> characters = sharedAssets.getCharacters();
        List<Monster> monsters = sharedAssets.getMonsters();

        tileInfo = new HashMap<>();

        map = new TiledMap();
        sharedAssets.setMap(map);
        MapLayers layers = map.getLayers();
        map.getProperties().put("tileInfo", tileInfo);

        tiles = new BoardTile(tileInfo);
        map.getProperties().put("tiles", tiles);

        shapeRenderer = new ShapeRenderer();

        TiledMapTileLayer layer = new TiledMapTileLayer(100, 100, 64, 32);

        layer.setName("tiles");
        layers.add(tiles.createStartTile(layer));

        characters.get(0).getSprite().setPosition(32f + characters.get(0).getOffsetX(), 16f);
        characters.get(0).getHighlightSprite().setPosition(32f + characters.get(0).getOffsetX(), 16f);
        characters.get(0).setCellX(0);
        characters.get(0).setCellY(1);
        characters.get(0).setTileX(0);
        characters.get(0).setTileY(0);
        characters.get(1).getSprite().setPosition(64f + characters.get(1).getOffsetX(), 32f);
        characters.get(1).getHighlightSprite().setPosition(64f + characters.get(1).getOffsetX(), 32f);
        characters.get(1).setCellX(0);
        characters.get(1).setCellY(2);
        characters.get(1).setTileX(0);
        characters.get(1).setTileY(0);
        characters.get(2).getSprite().setPosition(128f + characters.get(2).getOffsetX(), 32f);
        characters.get(2).getHighlightSprite().setPosition(128f + characters.get(2).getOffsetX(), 32f);
        characters.get(2).setCellX(1);
        characters.get(2).setCellY(3);
        characters.get(2).setTileX(0);
        characters.get(2).setTileY(0);
        characters.get(3).getSprite().setPosition(160f + characters.get(3).getOffsetX(), 16f);
        characters.get(3).getHighlightSprite().setPosition(160f + characters.get(3).getOffsetX(), 16f);
        characters.get(3).setCellX(2);
        characters.get(3).setCellY(3);
        characters.get(3).setTileX(0);
        characters.get(3).setTileY(0);

        monsters.get(0).getSprite().setPosition(288f + monsters.get(0).getOffsetX(), 80f);
        monsters.get(0).getHighlightSprite().setPosition(288f + monsters.get(0).getOffsetX(), 80f);
        monsters.get(0).setCellX(2);
        monsters.get(0).setCellY(7);
        monsters.get(0).setTileX(0);
        monsters.get(0).setTileY(1);

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

        List<Piece> allObjects = ListUtils.union(sharedAssets.getCharacters(), sharedAssets.getMonsters());
        allObjects.sort(spriteComparator);

        renderer.setView(camera);
        renderer.render();

        shapeRenderer.setProjectionMatrix(camera.combined);

        for (Integer x : tileInfo.keySet()) {
            for (Integer y : tileInfo.get(x).keySet()) {

                int cellX = tileInfo.get(x).get(y).getCellX();
                int cellY = tileInfo.get(x).get(y).getCellY();

                float x1 = (32 * cellX) + (32 * cellY);
                float y1 = (16 * cellY) - (16 * cellX) + 16f;
                float x2 = (32 * cellX) + (32 * (cellY + 3)) + 32f;
                float y2 = (16 * (cellY + 3)) - (16 * cellX) + 32f;
                float x3 = (32 * (cellX + 3)) + (32 * (cellY + 3)) + 64f;
                float y3 = (16 * (cellY + 3)) - (16 * (cellX + 3)) + 16f;
                float x4 = (32 * (cellX + 3)) + (32 * cellY) + 32f;
                float y4 = (16 * (cellY)) - (16 * (cellX + 3));

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1f, 0f, 0f, 1f);
                shapeRenderer.line(x1, y1, x2, y2);
                shapeRenderer.line(x2, y2, x3, y3);
                shapeRenderer.line(x3, y3, x4, y4);
                shapeRenderer.line(x1, y1, x4, y4);
                shapeRenderer.end();
            }
        }

        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.getBatch().begin();
        for (Piece piece : allObjects) {
            piece.getDrawSprite().draw(renderer.getBatch());
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

        if (sharedAssets.getPhase() == SharedAssets.Phase.HERO) {
            Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
            Vector3 position = camera.unproject(clickCoordinates, 0, Math.round(Gdx.graphics.getHeight() * .34f), Math.round(viewWidth), Math.round(viewHeight));
            Vector3 isoSpot = MapUtils.toIsometricGridPoint(position, 64f, 32f);

            int x = (int) isoSpot.x;
            int y = (int) isoSpot.y;

            InputEvent inputEvent = new InputEvent();
            inputEvent.setType(InputEvent.Type.touchDown);
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tiles");
            if (tiles.isHighlightTile(layer, x, y)) {
                DDUtils.movePC(sharedAssets.getCharacters(), layer, x, y);
                tiles.clearHighlightTiles(layer);
            } else {
                tiles.clearHighlightTiles(layer);
                List<Piece> allObjects = ListUtils.union(sharedAssets.getCharacters(), sharedAssets.getMonsters());
                for (Piece piece : allObjects) {
                    piece.setHighlighted(false);
                    if (piece.getCellX() == x && piece.getCellY() == y) {
                        piece.setHighlighted(true);
                        piece.getListener().clicked(inputEvent, x, y);
                        if (piece instanceof Monster) {
                            sharedAssets.getmName().setText(piece.getName());
                            sharedAssets.getmAC().setText(piece.getAc().toString());
                            sharedAssets.getmHP().setText(piece.getHp().toString());
                            sharedAssets.getMonsterPortrait().background(((Monster) piece).getPortrait());
                            sharedAssets.getMpAC().setText(piece.getAc().toString());
                            sharedAssets.getMpHP().setText(piece.getHp().toString());
                            sharedAssets.getmXP().setText(((Monster) piece).getXp().toString());
                            ((Monster) piece).updateTactics(sharedAssets.getMonsterTactics(), sharedAssets.getSkin());
                            ((Monster) piece).updateAttacks(sharedAssets.getMonsterAttacks(), sharedAssets.getSmallSkin(), sharedAssets.getmWidth());
                            sharedAssets.getLogArea().appendText(((Monster) piece).tactics(map, sharedAssets.getCharacters(), sharedAssets.getMonsters()));
                        }
                    }
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
