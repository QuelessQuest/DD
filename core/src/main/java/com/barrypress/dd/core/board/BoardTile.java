package com.barrypress.dd.core.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardTile {

    public enum TileType { FLOOR, WALL };
    public enum ROTATION { ROTATE_0, ROTATE_90, ROTATE_180, ROTATE_270 };

    private Texture tiles;
    private TextureRegion[][] splitTiles;
    private Map<TileType, TiledMapTileLayer.Cell> cells;
    private Tiles tileData;

    public BoardTile() {

        try {
            FileInputStream fis = new FileInputStream(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/board/assets/tiles.json").file());
            tileData = new Gson().fromJson(new InputStreamReader(fis), Tiles.class);
            fis.close();
        } catch (Exception e) {

        }

        tiles = new Texture(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/board/assets/iso2.png"));
        cells = new HashMap<>();
        splitTiles = TextureRegion.split(tiles, 64, 64);
        cells.put(TileType.FLOOR, new TiledMapTileLayer.Cell());
        cells.put(TileType.WALL, new TiledMapTileLayer.Cell());

        cells.get(TileType.FLOOR).setTile(new StaticTiledMapTile(splitTiles[0][0]));
        cells.get(TileType.WALL).setTile(new StaticTiledMapTile(splitTiles[0][1]));
    }

    public TiledMapTileLayer create(int tile, int x, int y, ROTATION rotation) {

        TiledMapTileLayer layer = new TiledMapTileLayer(8, 8, 64, 32);
        List<TileType> types = rotate(rotation, tileData.getTypes(tile));

        layer.setCell(x, y, cells.get(types.get(0)));
        layer.setCell(x, 1 + y, cells.get(types.get(1)));
        layer.setCell(x, 2 + y, cells.get(types.get(2)));
        layer.setCell(x, 3 + y, cells.get(types.get(3)));

        layer.setCell(1 + x, y, cells.get(types.get(4)));
        layer.setCell(1 + x, 1 + y, cells.get(types.get(5)));
        layer.setCell(1 + x, 2 + y, cells.get(types.get(6)));
        layer.setCell(1 + x, 3 + y, cells.get(types.get(7)));

        layer.setCell(2 + x, y, cells.get(types.get(8)));
        layer.setCell(2 + x, 1 + y, cells.get(types.get(9)));
        layer.setCell(2 + x, 2 + y, cells.get(types.get(10)));
        layer.setCell(2 + x, 3 + y, cells.get(types.get(11)));

        layer.setCell(3 + x, y, cells.get(types.get(12)));
        layer.setCell(3 + x, 1 + y, cells.get(types.get(13)));
        layer.setCell(3 + x, 2 + y, cells.get(types.get(14)));
        layer.setCell(3 + x, 3 + y, cells.get(types.get(15)));

        return layer;
    }

    public List<TileType> rotate(ROTATION rotation, List<TileType> tileTypes) {

        List<TileType> rotated = null;

        switch (rotation) {
            case ROTATE_0:
                rotated = tileTypes;
                break;
            case ROTATE_90:
                rotated = new ArrayList<>();
                rotated.add(tileTypes.get(12));
                rotated.add(tileTypes.get(8));
                rotated.add(tileTypes.get(4));
                rotated.add(tileTypes.get(0));
                rotated.add(tileTypes.get(13));
                rotated.add(tileTypes.get(9));
                rotated.add(tileTypes.get(5));
                rotated.add(tileTypes.get(1));
                rotated.add(tileTypes.get(14));
                rotated.add(tileTypes.get(10));
                rotated.add(tileTypes.get(6));
                rotated.add(tileTypes.get(2));
                rotated.add(tileTypes.get(15));
                rotated.add(tileTypes.get(11));
                rotated.add(tileTypes.get(7));
                rotated.add(tileTypes.get(3));
                break;
            case ROTATE_180:
                rotated = new ArrayList<>();
                for (int i = tileTypes.size() - 1; i >= 0; i--) {
                    rotated.add(tileTypes.get(i));
                }

                break;
            case ROTATE_270:
                rotated = new ArrayList<>();
                rotated.add(tileTypes.get(3));
                rotated.add(tileTypes.get(7));
                rotated.add(tileTypes.get(11));
                rotated.add(tileTypes.get(15));
                rotated.add(tileTypes.get(2));
                rotated.add(tileTypes.get(6));
                rotated.add(tileTypes.get(10));
                rotated.add(tileTypes.get(14));
                rotated.add(tileTypes.get(1));
                rotated.add(tileTypes.get(5));
                rotated.add(tileTypes.get(9));
                rotated.add(tileTypes.get(13));
                rotated.add(tileTypes.get(0));
                rotated.add(tileTypes.get(4));
                rotated.add(tileTypes.get(8));
                rotated.add(tileTypes.get(12));
                break;
        }

        return rotated;
    }
}
