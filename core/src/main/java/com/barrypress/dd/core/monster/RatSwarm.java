package com.barrypress.dd.core.monster;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.utility.DDUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.List;

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
        setPortrait(new SpriteDrawable(new Sprite(spriteSheet.findRegion("p_rat_swarm"))));
        setOffsetX(10f);
        setOffsetY(-2f);

        addTactic("If the Rat Swarm is within 1 tile of a Hero,",
                " it moves to the closest Hero's tile and attacks each Hero on the tile with a multitudes of bites.\n\n");
        addTactic("Otherwise,", " the Rat Swarm moves 1 tile toward the closest Hero.");

        addAttack(new OnlyDamage("Bite", 7, 1));
    }

    public String tactics(TiledMap map, List<PC> characters, List<Monster> monsters) {

        List<Piece> allObjects = ListUtils.union(characters, monsters);
        String results = getName() + " activates\n";

        PC nearest = (PC) getNearestOnTile(map, characters);
        if (nearest != null) {
            if (!DDUtils.nextTo(this, nearest)) {
                DDUtils.move(map, this, nearest, allObjects);
                results += getName() + " moves next to " + nearest.getName() + "\n";
            }
            return results + attack(getAttacks().get(0), characters);
        } else {
            nearest = (PC) getNearestWithinXTiles(map, 1, characters);
            if (nearest != null) {
                DDUtils.move(map, this, nearest, allObjects);
                results += getName() + " moves 1 tile then next to " + nearest.getName() + "\n";
                return results + attack(getAttacks().get(0), characters);
            } else {
                DDUtils.moveTowardNearest(map, this, getTileX(), getTileY(), characters);
                return results + getName() + " moves closer.......";
            }
        }
    }

    private String attack(Attack attack, List<PC> characters) {

        String result = "";

        for (PC pc : characters) {
            if (pc.getTileX() == getTileX() && pc.getTileY() == getTileY()) {
                int roll = DDUtils.rolld20();

                if ((roll + attack.getAttack()) >= pc.getAc()) {
                    result += getName() + " " + attack.getName() + "s " + pc.getName() + " for " + attack.getDamage();
                    result += " (" + roll + " + " + attack.getAttack() + ")\n";
                    pc.takeDamage(attack.getDamage());
                } else {
                    result += getName() + " attempts to " + attack.getName() + " " + pc.getName() + " and misses.\n";
                }
            }
        }

        return result;
    }

}
