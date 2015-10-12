package com.barrypress.dd.core.monster;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.board.Tile;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.utility.DDUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RatSwarm extends Monster {

    public RatSwarm(TextureAtlas spriteSheet) {
        init();
        setName("Rat Swarm");
        setType("Animal");
        setAc(12);
        setHp(1);
        setXp(1);
        setSprite(new Sprite(spriteSheet.findRegion("rat_swarm")));
        setHighlightSprite(new Sprite(spriteSheet.findRegion("g_rat_swarm")));
        setOffsetX(10f);
        setOffsetY(-2f);

        addTactic("If the Rat Swarm is within 1 tile of a Hero,",
                " it moves to the closest Hero's tile and attacks each Hero on the tile with a multitudes of bites.\n\n");
        addTactic("Otherwise,", " the Rat Swarm moves 1 tile toward the closest Hero.");

        addAttack(new OnlyDamage("Bite", 7, 1));
    }

    public String tactics(TiledMap map, List<PC> characters, List<Monster> monsters) {

        List<Piece> allObjects = ListUtils.union(characters, monsters);

        PC nearest = (PC) getNearestOnTile(characters);
        if (nearest != null) {
            move(map, nearest, allObjects);
            return attack(getAttacks().get(0), nearest);
        } else {
            nearest = (PC) getNearestWithinXTiles(1, characters);
            if (nearest != null) {
                move(map, nearest, allObjects);
                return attack(getAttacks().get(0), nearest);
            } else {
                DDUtils.moveTowardNearest(map, this, getTileX(), getTileY(), characters);
                return getName() + " moves closer.......";
            }
        }
    }

    private String attack(Attack attack, PC pc) {

        String result = getName();

        int roll = DDUtils.rolld20();

        if ((roll + attack.getAttack()) >= pc.getAc()) {
            result += " " + attack.getName() + " " + pc.getName() + " for " + attack.getDamage();
            pc.takeDamage(attack.getDamage());
        } else {
            result += " attempts to " + attack.getName() + " " + pc.getName() + " and misses.";
        }

        return result;
    }

}
