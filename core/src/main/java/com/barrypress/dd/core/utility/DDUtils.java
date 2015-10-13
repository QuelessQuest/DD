package com.barrypress.dd.core.utility;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.board.BoardTile;
import com.barrypress.dd.core.character.PC;

import java.util.List;
import java.util.Random;

public class DDUtils {

    public static int rolld20() {
        Random rand = new Random();
        return rand.nextInt((20 - 1) + 1) + 1;
    }

    public static boolean isAvailable(List<? extends Piece> objects, int x, int y) {

        for (Piece piece : objects) {
            if (piece.getCellX() == x && piece.getCellY() == y) return false;
        }
        return true;
    }

    public static boolean hasFreeSpaceAround(TiledMap map, Piece piece, List<? extends Piece> pieces) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tiles");

        for (int x = piece.getCellX() - 1; x < piece.getCellX() + 1; x++) {
            for (int y = piece.getCellY() - 1; y < piece.getCellY() + 1; y++) {
                if (layer.getCell(x, y) != null) {
                    if (layer.getCell(x, y).getTile() != null) {
                        Boolean wall = (Boolean) layer.getCell(x, y).getTile().getProperties().get("target");
                        if (wall != null && !wall) {
                            if (isAvailable(pieces, x, y)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean tileContains(int tX, int tY, Piece piece) {

        int llCellX = tX * 4;
        int llCellY = tY * 4;
        int urCellX = llCellX + 3;
        int urCellY = llCellY + 3;

        return (piece.getCellX() >= llCellX && piece.getCellX() <= urCellX && piece.getCellY() >= llCellY && piece.getCellY() <= urCellY);
    }

    public static boolean tileContains(int tX, int tY, List<? extends Piece> pieces) {

        int llCellX = tX * 4;
        int llCellY = tY * 4;
        int urCellX = llCellX + 3;
        int urCellY = llCellY + 3;

        for (Piece piece : pieces) {
            if (piece.getCellX() >= llCellX && piece.getCellX() <= urCellX && piece.getCellY() >= llCellY && piece.getCellY() <= urCellY) {
                return true;
            }
        }

        return false;
    }

    public static Piece getNearestOnTile(TiledMap map, int tX, int tY, List<? extends Piece> pieces) {
        return getNearestOnTile(map, tX, tY, (tX * 4) + 1, (tY * 4) + 1, pieces);
    }

    public static Piece getNearestOnTile(TiledMap map, int tX, int tY, int cX, int cY, List<? extends Piece> pieces) {

        int closeX = 4;
        int closeY = 4;
        Piece nearest = null;

        for (Piece piece : pieces) {
            if (piece.getTileX() == tX && piece.getTileY() == tY) {
                int compareX = Math.abs(cX - piece.getCellX());
                int compareY = Math.abs(cY - piece.getCellY());
                if (compareX < closeX && compareY < closeY) {
                    if (hasFreeSpaceAround(map, piece, pieces)) {
                        nearest = piece;
                        closeX = piece.getCellX();
                        closeY = piece.getCellY();
                    }
                }
            }
        }

        return nearest;
    }

    public static Piece getNearestWithinXTiles(TiledMap map, int distance, int tX, int tY, List<? extends Piece> pieces) {

        distance -= 1;

        // N
        if (tileContains(tX, tY + 1, pieces)) {
            return getNearestOnTile(map, tX, tY + 1, pieces);
        } else if (tileContains(tX + 1, tY, pieces)) {
            return getNearestOnTile(map, tX + 1, tY, pieces);
        } else if (tileContains(tX, tY - 1, pieces)) {
            return getNearestOnTile(map, tX, tY - 1, pieces);
        } else if (tileContains(tX - 1, tY, pieces)) {
            return getNearestOnTile(map, tX - 1, tY, pieces);
        }

        if (distance > 0) {
            Piece nearest = getNearestWithinXTiles(map, distance, tX, tY + 1, pieces);
            if (nearest == null) nearest = getNearestWithinXTiles(map, distance, tX + 1, tY, pieces);
            if (nearest == null) nearest = getNearestWithinXTiles(map, distance, tX, tY - 1, pieces);
            if (nearest == null) nearest = getNearestWithinXTiles(map, distance, tX - 1, tY, pieces);
            return nearest;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static void moveTo(TiledMap map, Piece me, int tX, int tY) {

        int cellX = 0;
        int cellY = 0;

        // Find portal cell
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tiles");

        for (int x = tX * 4; x < (tX * 4) + 4; x++) {
            for (int y = tY * 4; y < (tY * 4) + 4; y++) {
                if (layer.getCell(x, y) != null) {
                    if (layer.getCell(x, y).getTile() != null) {
                        if (layer.getCell(x, y).getTile().getProperties().get("type") == BoardTile.TileType.PORTAL) {
                            cellX = x;
                            cellY = y;
                            break;
                        }
                    }
                }
            }
        }

        float x = (32 * cellX) + (32 * cellY);
        float y = (16 * cellY) - (16 * cellX);

        me.setCellX(cellX);
        me.setCellY(cellY);
        me.setTileX(tX);
        me.setTileY(tY);

        me.getSprite().setPosition(x, y);
        me.getHighlightSprite().setPosition(x, y);
    }

    public static void moveTowardNearest(TiledMap map, Piece me, int tX, int tY, List<? extends  Piece> pieces) {

        int xShift = 200;
        int yShift = 200;

        for (Piece piece : pieces) {
            int x = piece.getTileX() - tX;
            int y = piece.getTileY() - tY;
            if (Math.abs(x) < Math.abs(xShift)) xShift = x;
            if (Math.abs(y) < Math.abs(yShift)) yShift = y;
        }

        if (Math.abs(xShift) <= Math.abs(yShift)) moveTo(map, me, me.getTileX() + xShift, me.getTileY());
        else moveTo(map, me, me.getTileX(), me.getTileY() + yShift);
    }

    public static void move(TiledMap map, Piece me, Piece them, List<Piece> pieces) {

        int x = them.getCellX();
        int y = them.getCellY();

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("tiles");

        for (int x1 = x - 1; x1 <= x + 1; x1++) {
            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                if (x1 == x && y1 == y) continue;
                if (layer.getCell(x1, y1) == null) continue;
                Boolean wall = (Boolean) layer.getCell(x1, y1).getTile().getProperties().get("target");
                if (!wall && DDUtils.isAvailable(pieces, x1, y1)) {
                    me.setCellX(x1);
                    me.setCellY(y1);
                    me.setTileX(x1 == 0 ? 0 : x1 / 4);
                    me.setTileY(y1 == 0 ? 0 : y1 / 4);
                    float mX = (32 * x1) + (32 * y1);
                    float mY = (16 * y1) - (16 * x1);
                    me.getSprite().setPosition(mX, mY);
                    me.getHighlightSprite().setPosition(mX, mY);
                    return;
                }
            }
        }
    }

    public static void movePC(List<PC> characters, TiledMapTileLayer layer, int x, int y) {

        float x1 = (32 * x) + (32 * y);
        float y1 = (16 * y) - (16 * x);

        if (layer.getCell(x, y) != null) {
            if (layer.getCell(x, y).getTile() != null) {
                Boolean wall = (Boolean) layer.getCell(x, y).getTile().getProperties().get("target");

                if (wall != null && !wall) {
                    for (PC pc : characters) {
                        if (pc.isHighlighted()) {
                            pc.getSprite().setPosition(x1 + pc.getOffsetX(), y1 + pc.getOffsetY());
                            pc.getHighlightSprite().setPosition(x1 + pc.getOffsetX(), y1 + pc.getOffsetY());
                            pc.setCellX(x);
                            pc.setCellY(y);
                            pc.setTileX(x == 0 ? 0 : x/4);
                            pc.setTileY(y == 0 ? 0 : y/4);
                            break;
                        }
                    }
                }
            }
        }
    }
}
