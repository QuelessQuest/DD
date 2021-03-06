package com.barrypress.dd.core.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.utility.DDUtils;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardTile {

    public enum TileType {FLOOR, WALL, PORTAL, HIGHLIGHT, STAIR, PORTAL_HIGHLIGHT}
    public enum ROTATION {ROTATE_0, ROTATE_90, ROTATE_180, ROTATE_270}

    private TextureRegion[][] splitTiles;
    private TilesFromJson tileData;
    private Map<Integer, Map<Integer, Tile>> tiles;

    @SuppressWarnings("unchecked")
    public BoardTile(Map<Integer, Map<Integer, Tile>> tileInfo) {

        tiles = tileInfo;

        try {
            FileInputStream fis = new FileInputStream(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/board/assets/tiles.json").file());
            tileData = new Gson().fromJson(new InputStreamReader(fis), TilesFromJson.class);
            fis.close();
        } catch (Exception e) {
            System.out.println("Error reading tiles file");
        }

        splitTiles = TextureRegion.split(new Texture(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/board/assets/iso2.png")), 64, 64);
    }

    private TiledMapTileLayer.Cell makeCell(TileType type) {

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        StaticTiledMapTile tile = null;

        switch (type) {
            case FLOOR:
                tile = new StaticTiledMapTile(splitTiles[0][0]);
                tile.getProperties().put("target", false);
                tile.getProperties().put("type", TileType.FLOOR);
                break;
            case WALL:
                tile = new StaticTiledMapTile(splitTiles[0][1]);
                tile.getProperties().put("target", true);
                tile.getProperties().put("type", TileType.WALL);
                break;
            case PORTAL:
                tile = new StaticTiledMapTile(splitTiles[1][0]);
                tile.getProperties().put("target", false);
                tile.getProperties().put("type", TileType.PORTAL);
                break;
            case HIGHLIGHT:
                tile = new StaticTiledMapTile(splitTiles[0][2]);
                tile.getProperties().put("target", false);
                tile.getProperties().put("type", TileType.HIGHLIGHT);
                break;
            case STAIR:
                tile = new StaticTiledMapTile(splitTiles[0][3]);
                tile.getProperties().put("target", true);
                tile.getProperties().put("type", TileType.STAIR);
                break;
            case PORTAL_HIGHLIGHT:
                tile = new StaticTiledMapTile(splitTiles[1][1]);
                tile.getProperties().put("target", false);
                tile.getProperties().put("type", TileType.PORTAL_HIGHLIGHT);
                break;
        }

        cell.setTile(tile);
        return cell;
    }

    public TiledMapTileLayer createTile(TiledMapTileLayer layer, int tile, int x, int y, ROTATION rotation) {

        List<TileType> types = rotate(rotation, tileData.getTypes(tile));
        Tile aTile = new Tile(tile, x, y);

        if (tiles.get(x+1) != null && tiles.get(x+1).get(y) != null) {
            aTile.setTileE(tiles.get(x+1).get(y));
            tiles.get(x+1).get(y).setTileW(aTile);
        }
        if (tiles.get(x) != null && tiles.get(x).get(y-1) != null) {
            aTile.setTileS(tiles.get(x).get(y-1));
            tiles.get(x).get(y-1).setTileN(aTile);
        }
        if (tiles.get(x-1) != null && tiles.get(x-1).get(y-1) != null) {
            aTile.setTileW(tiles.get(x-1).get(y-1));
            tiles.get(x-1).get(y-1).setTileE(aTile);
        }
        if (tiles.get(x) != null && tiles.get(x).get(y+1) != null) {
            aTile.setTileN(tiles.get(x).get(y+1));
            tiles.get(x).get(y+1).setTileS(aTile);
        }

        int cX = x * 4;
        int cY = y * 4;

        layer.setCell(cX, cY, makeCell(types.get(0)));
        layer.setCell(cX, 1 + cY, makeCell(types.get(1)));
        layer.setCell(cX, 2 + cY, makeCell(types.get(2)));
        layer.setCell(cX, 3 + cY, makeCell(types.get(3)));

        layer.setCell(1 + cX, cY, makeCell(types.get(4)));
        layer.setCell(1 + cX, 1 + cY, makeCell(types.get(5)));
        layer.setCell(1 + cX, 2 + cY, makeCell(types.get(6)));
        layer.setCell(1 + cX, 3 + cY, makeCell(types.get(7)));

        layer.setCell(2 + cX, cY, makeCell(types.get(8)));
        layer.setCell(2 + cX, 1 + cY, makeCell(types.get(9)));
        layer.setCell(2 + cX, 2 + cY, makeCell(types.get(10)));
        layer.setCell(2 + cX, 3 + cY, makeCell(types.get(11)));

        layer.setCell(3 + cX, cY, makeCell(types.get(12)));
        layer.setCell(3 + cX, 1 + cY, makeCell(types.get(13)));
        layer.setCell(3 + cX, 2 + cY, makeCell(types.get(14)));
        layer.setCell(3 + cX, 3 + cY, makeCell(types.get(15)));

        return layer;
    }

    public TiledMapTileLayer createStartTile(TiledMapTileLayer layer) {

        Tile sTile1 = new Tile(0, 0, 0);
        Tile sTile2 = new Tile(0, 0, 4);

        sTile1.setTileN(sTile2);
        sTile2.setTileS(sTile1);

        Map<Integer, Tile> newTile = new HashMap<>();
        newTile.put(0, sTile1);
        newTile.put(1, sTile2);

        tiles.put(0, newTile);

        layer.setCell(0, 0, makeCell(TileType.WALL));
        layer.setCell(0, 1, makeCell(TileType.FLOOR));
        layer.setCell(0, 2, makeCell(TileType.FLOOR));
        layer.setCell(0, 3, makeCell(TileType.FLOOR));
        layer.setCell(0, 4, makeCell(TileType.FLOOR));
        layer.setCell(0, 5, makeCell(TileType.FLOOR));
        layer.setCell(0, 6, makeCell(TileType.PORTAL));
        layer.setCell(0, 7, makeCell(TileType.FLOOR));

        layer.setCell(1, 0, makeCell(TileType.STAIR));
        layer.setCell(1, 1, makeCell(TileType.STAIR));
        layer.setCell(1, 2, makeCell(TileType.STAIR));
        layer.setCell(1, 3, makeCell(TileType.FLOOR));
        layer.setCell(1, 4, makeCell(TileType.FLOOR));
        layer.setCell(1, 5, makeCell(TileType.FLOOR));
        layer.setCell(1, 6, makeCell(TileType.FLOOR));
        layer.setCell(1, 7, makeCell(TileType.FLOOR));

        layer.setCell(2, 0, makeCell(TileType.STAIR));
        layer.setCell(2, 1, makeCell(TileType.STAIR));
        layer.setCell(2, 2, makeCell(TileType.STAIR));
        layer.setCell(2, 3, makeCell(TileType.FLOOR));
        layer.setCell(2, 4, makeCell(TileType.FLOOR));
        layer.setCell(2, 5, makeCell(TileType.FLOOR));
        layer.setCell(2, 6, makeCell(TileType.FLOOR));
        layer.setCell(2, 7, makeCell(TileType.FLOOR));

        layer.setCell(3, 0, makeCell(TileType.WALL));
        layer.setCell(3, 1, makeCell(TileType.FLOOR));
        layer.setCell(3, 2, makeCell(TileType.PORTAL));
        layer.setCell(3, 3, makeCell(TileType.FLOOR));
        layer.setCell(3, 4, makeCell(TileType.FLOOR));
        layer.setCell(3, 5, makeCell(TileType.FLOOR));
        layer.setCell(3, 6, makeCell(TileType.FLOOR));
        layer.setCell(3, 7, makeCell(TileType.FLOOR));

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

    public boolean isHighlightTile(TiledMapTileLayer layer, int x, int y) {
        if (layer.getCell(x, y) != null) {
            if (layer.getCell(x, y).getTile() != null) {
                TileType tileType = (TileType) layer.getCell(x, y).getTile().getProperties().get("type");
                return (tileType == TileType.HIGHLIGHT || tileType == TileType.PORTAL_HIGHLIGHT);
            }
        }
        return false;
    }

    public void clearHighlightTiles(TiledMapTileLayer layer) {
        int x = layer.getWidth();
        int y = layer.getHeight();

        for (int x1 = 0; x1 < x; x1++) {
            for (int y1 = 0; y1 < y; y1++) {
                if (layer.getCell(x1, y1) != null) {
                    if (layer.getCell(x1, y1).getTile() != null) {
                        if (layer.getCell(x1, y1).getTile().getProperties().get("oldtype") != null) {
                            StaticTiledMapTile tile = null;
                            switch ((TileType) layer.getCell(x1, y1).getTile().getProperties().get("oldtype")) {
                                case FLOOR:
                                    tile = new StaticTiledMapTile((splitTiles[0][0]));
                                    tile.getProperties().put("type", TileType.FLOOR);
                                    tile.getProperties().put("target", false);
                                    break;
                                case PORTAL:
                                    tile = new StaticTiledMapTile((splitTiles[1][0]));
                                    tile.getProperties().put("type", TileType.PORTAL);
                                    tile.getProperties().put("target", false);
                                    break;
                                case HIGHLIGHT:
                                    tile = new StaticTiledMapTile((splitTiles[0][0]));
                                    tile.getProperties().put("type", TileType.FLOOR);
                                    tile.getProperties().put("target", false);
                                    break;
                                case PORTAL_HIGHLIGHT:
                                    tile = new StaticTiledMapTile((splitTiles[1][0]));
                                    tile.getProperties().put("type", TileType.PORTAL);
                                    tile.getProperties().put("target", false);
                                    break;
                            }
                            layer.getCell(x1, y1).setTile(tile);
                        }
                    }
                }
            }
        }
    }

    public void highlightTiles(List<? extends Piece> objects, TiledMapTileLayer layer, int x, int y, int range) {

        range -= 1;

        for (int x1 = x - 1; x1 <= x + 1; x1++) {
            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                if (x1 == x && y1 == y) continue;

                if (layer.getCell(x1, y1) != null) {
                    if (layer.getCell(x1, y1).getTile() != null) {
                        Boolean use = (Boolean) layer.getCell(x1, y1).getTile().getProperties().get("target");
                        if (!use) {
                            if (DDUtils.isAvailable(objects, x1, y1)) {
                                TileType tileType = (TileType) layer.getCell(x1, y1).getTile().getProperties().get("type");
                                StaticTiledMapTile hTile;
                                if (tileType == TileType.PORTAL || tileType == TileType.PORTAL_HIGHLIGHT) {
                                    hTile = new StaticTiledMapTile(splitTiles[1][1]);
                                    hTile.getProperties().put("type", TileType.PORTAL_HIGHLIGHT);
                                } else {
                                    hTile = new StaticTiledMapTile(splitTiles[0][2]);
                                    hTile.getProperties().put("type", TileType.HIGHLIGHT);
                                }
                                hTile.getProperties().put("oldtype", tileType);
                                hTile.getProperties().put("target", false);

                                layer.getCell(x1, y1).setTile(hTile);
                            }
                            if (range > 0) {
                                highlightTiles(objects, layer, x1, y1, range);
                            }
                        }
                    }
                }
            }
        }
    }

    public Map<Integer, Map<Integer, Tile>> getTiles() {
        return tiles;
    }
}
