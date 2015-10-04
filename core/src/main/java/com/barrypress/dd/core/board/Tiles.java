package com.barrypress.dd.core.board;

import java.util.ArrayList;
import java.util.List;

public class Tiles {

    public class Details {
        private int index;
        private List<BoardTile.TileType> type;

        public Details() {
            type = new ArrayList<>();
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<BoardTile.TileType> getType() {
            return type;
        }

        public void setType(List<BoardTile.TileType> type) {
            this.type = type;
        }
    }

    private List<Details> tiles;

    public Tiles() {
        tiles = new ArrayList<>();
    }

    public List<Details> getTiles() {
        return tiles;
    }

    public void setTiles(List<Details> tiles) {
        this.tiles = tiles;
    }

    public List<BoardTile.TileType> getTypes(int index) {

        List<BoardTile.TileType> types = null;

        for (Details details: tiles) {
            if (details.getIndex() == index) {
                types = details.getType();
                break;
            }
        }

        return types;
    }
}