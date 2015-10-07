package com.barrypress.dd.core.utility;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class DDIsometricTiledMapRenderer extends IsometricTiledMapRenderer {

    public DDIsometricTiledMapRenderer(TiledMap map) {
        super(map);
    }

    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            getBatch().draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY());
        }
    }
}
