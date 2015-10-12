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

    public static Piece getNearestOnTile(int tX, int tY, List<? extends Piece> pieces) {
        return getNearestOnTile(tX, tY, (tX * 4) + 1, (tY * 4) + 1, pieces);
    }

    public static Piece getNearestOnTile(int tX, int tY, int cX, int cY, List<? extends Piece> pieces) {

        int closeX = 4;
        int closeY = 4;
        Piece nearest = null;

        for (Piece piece : pieces) {
            if (piece.getTileX() == tX && piece.getTileY() == tY) {
                int compareX = Math.abs(cX - piece.getCellX());
                int compareY = Math.abs(cY - piece.getCellY());
                if (compareX < closeX && compareY < closeY) {
                    nearest = piece;
                    closeX = piece.getCellX();
                    closeY = piece.getCellY();
                }
            }
        }

        return nearest;
    }

    public static Piece getNearestWithinXTiles(int distance, int tX, int tY, List<? extends Piece> pieces) {

        distance -= 1;

        // N
        if (tileContains(tX, tY + 1, pieces)) {
            return getNearestOnTile(tX, tY + 1, pieces);
        } else if (tileContains(tX + 1, tY, pieces)) {
            return getNearestOnTile(tX + 1, tY, pieces);
        } else if (tileContains(tX, tY - 1, pieces)) {
            return getNearestOnTile(tX, tY - 1, pieces);
        } else if (tileContains(tX - 1, tY, pieces)) {
            return getNearestOnTile(tX - 1, tY, pieces);
        }

        if (distance > 0) {
            Piece nearest = getNearestWithinXTiles(distance, tX, tY + 1, pieces);
            if (nearest == null) nearest = getNearestWithinXTiles(distance, tX + 1, tY, pieces);
            if (nearest == null) nearest = getNearestWithinXTiles(distance, tX, tY - 1, pieces);
            if (nearest == null) nearest = getNearestWithinXTiles(distance, tX - 1, tY, pieces);
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
