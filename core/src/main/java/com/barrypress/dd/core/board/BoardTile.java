package com.barrypress.dd.core.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
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

    public enum TileType { FLOOR, WALL, PORTAL, HIGHLIGHT, STAIR };
    public enum ROTATION { ROTATE_0, ROTATE_90, ROTATE_180, ROTATE_270 };

    private Texture tiles;
    private TextureRegion[][] splitTiles;
    private Map<TileType, TiledMapTileLayer.Cell> cellTypeMap;
    private Tiles tileData;
    private StaticTiledMapTile floorTile;
    private StaticTiledMapTile wallTile;
    private StaticTiledMapTile portalTile;
    private StaticTiledMapTile highlightTile;
    private StaticTiledMapTile stairTile;
    private StaticTiledMapTile highlightPortalTile;

    public BoardTile() {

        try {
            FileInputStream fis = new FileInputStream(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/board/assets/tiles.json").file());
            tileData = new Gson().fromJson(new InputStreamReader(fis), Tiles.class);
            fis.close();
        } catch (Exception e) {

        }

        tiles = new Texture(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/board/assets/iso2.png"));
        cellTypeMap = new HashMap<>();
        splitTiles = TextureRegion.split(tiles, 64, 64);
        cellTypeMap.put(TileType.FLOOR, new TiledMapTileLayer.Cell());
        cellTypeMap.put(TileType.WALL, new TiledMapTileLayer.Cell());
        cellTypeMap.put(TileType.PORTAL, new TiledMapTileLayer.Cell());
        cellTypeMap.put(TileType.HIGHLIGHT, new TiledMapTileLayer.Cell());
        cellTypeMap.put(TileType.STAIR, new TiledMapTileLayer.Cell());

        floorTile = new StaticTiledMapTile(splitTiles[0][0]);
        floorTile.getProperties().put("target", false);
        floorTile.getProperties().put("type", TileType.FLOOR);
        wallTile = new StaticTiledMapTile(splitTiles[0][1]);
        wallTile.getProperties().put("target", true);
        wallTile.getProperties().put("type", TileType.WALL);
        portalTile = new StaticTiledMapTile(splitTiles[1][0]);
        portalTile.getProperties().put("target", false);
        portalTile.getProperties().put("type", TileType.PORTAL);
        highlightTile = new StaticTiledMapTile(splitTiles[0][2]);
        highlightTile.getProperties().put("target", false);
        highlightTile.getProperties().put("type", TileType.HIGHLIGHT);
        stairTile = new StaticTiledMapTile(splitTiles[0][3]);
        stairTile.getProperties().put("target", true);
        stairTile.getProperties().put("type", TileType.STAIR);

        cellTypeMap.get(TileType.FLOOR).setTile(floorTile);
        cellTypeMap.get(TileType.WALL).setTile(wallTile);
        cellTypeMap.get(TileType.PORTAL).setTile(portalTile);
        cellTypeMap.get(TileType.HIGHLIGHT).setTile(highlightTile);
        cellTypeMap.get(TileType.STAIR).setTile(stairTile);
    }

    public TiledMapTileLayer createTile(TiledMapTileLayer layer, int tile, int x, int y, ROTATION rotation) {

        List<TileType> types = rotate(rotation, tileData.getTypes(tile));

        layer.setCell(x, y, cellTypeMap.get(types.get(0)));
        layer.setCell(x, 1 + y, cellTypeMap.get(types.get(1)));
        layer.setCell(x, 2 + y, cellTypeMap.get(types.get(2)));
        layer.setCell(x, 3 + y, cellTypeMap.get(types.get(3)));

        layer.setCell(1 + x, y, cellTypeMap.get(types.get(4)));
        layer.setCell(1 + x, 1 + y, cellTypeMap.get(types.get(5)));
        layer.setCell(1 + x, 2 + y, cellTypeMap.get(types.get(6)));
        layer.setCell(1 + x, 3 + y, cellTypeMap.get(types.get(7)));

        layer.setCell(2 + x, y, cellTypeMap.get(types.get(8)));
        layer.setCell(2 + x, 1 + y, cellTypeMap.get(types.get(9)));
        layer.setCell(2 + x, 2 + y, cellTypeMap.get(types.get(10)));
        layer.setCell(2 + x, 3 + y, cellTypeMap.get(types.get(11)));

        layer.setCell(3 + x, y, cellTypeMap.get(types.get(12)));
        layer.setCell(3 + x, 1 + y, cellTypeMap.get(types.get(13)));
        layer.setCell(3 + x, 2 + y, cellTypeMap.get(types.get(14)));
        layer.setCell(3 + x, 3 + y, cellTypeMap.get(types.get(15)));

        return layer;
    }

    public TiledMapTileLayer createStartTile(TiledMapTileLayer layer) {

        layer.setCell(0, 0, cellTypeMap.get(TileType.WALL));
        layer.setCell(0, 1, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(0, 2, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(0, 3, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(0, 4, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(0, 5, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(0, 6, cellTypeMap.get(TileType.PORTAL));
        layer.setCell(0, 7, cellTypeMap.get(TileType.FLOOR));

        layer.setCell(1, 0, cellTypeMap.get(TileType.STAIR));
        layer.setCell(1, 1, cellTypeMap.get(TileType.STAIR));
        layer.setCell(1, 2, cellTypeMap.get(TileType.STAIR));
        layer.setCell(1, 3, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(1, 4, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(1, 5, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(1, 6, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(1, 7, cellTypeMap.get(TileType.FLOOR));

        layer.setCell(2, 0, cellTypeMap.get(TileType.STAIR));
        layer.setCell(2, 1, cellTypeMap.get(TileType.STAIR));
        layer.setCell(2, 2, cellTypeMap.get(TileType.STAIR));
        layer.setCell(2, 3, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(2, 4, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(2, 5, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(2, 6, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(2, 7, cellTypeMap.get(TileType.FLOOR));

        layer.setCell(3, 0, cellTypeMap.get(TileType.WALL));
        layer.setCell(3, 1, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(3, 2, cellTypeMap.get(TileType.PORTAL));
        layer.setCell(3, 3, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(3, 4, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(3, 5, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(3, 6, cellTypeMap.get(TileType.FLOOR));
        layer.setCell(3, 7, cellTypeMap.get(TileType.FLOOR));

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

    public void highlightTiles(TiledMapTileLayer layer, int x, int y, int range) {

        for (int x1 = x - 1; x1 <= x + 1; x1++) {
            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                if (x1 == x && y1 == y) continue;

                if (layer.getCell(x1, y1) != null) {
                    if (layer.getCell(x1, y1).getTile() != null) {
                        TiledMapTileLayer.Cell cell = layer.getCell(x1, y1);
                        Boolean use = (Boolean) layer.getCell(x1, y1).getTile().getProperties().get("target");
                        if (!use) {
                            TileType tileType = (TileType) layer.getCell(x1, y1).getTile().getProperties().get("type");
                            StaticTiledMapTile hTile = new StaticTiledMapTile(splitTiles[0][2]);
                            hTile.getProperties().put("oldtype", tileType);
                            hTile.setId(2);
                            layer.getCell(x1, y1).setTile(hTile);
                        }
                    }
                }
            }
        }
    }

    public Map<TileType, TiledMapTileLayer.Cell> getCellTypeMap() {
        return cellTypeMap;
    }

    public void setCellTypeMap(Map<TileType, TiledMapTileLayer.Cell> cellTypeMap) {
        this.cellTypeMap = cellTypeMap;
    }

    public TextureRegion[][] getSplitTiles() {
        return splitTiles;
    }

    public void setSplitTiles(TextureRegion[][] splitTiles) {
        this.splitTiles = splitTiles;
    }
}
