package com.barrypress.dd.core.monster;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RatSwarm extends Monster {

    public RatSwarm(TextureAtlas spriteSheet) {
        init();
        setName("Rat Swarm");
        setType("Animal");
        setAc(12);
        setHp(1);
        setSprite(new Sprite(spriteSheet.findRegion("rat_swarm")));
        setOffsetX(10f);
        setOffsetY(-2f);

        addTactic("If the Rat Swarm is within 1 tile of a Hero,",
                "it moves to the closest Hero's tile and attacks each Hero on the tile with a multitudes of bites.");
        addTactic("Otherwise,", "the Rat Swarm moves 1 tile toward the closest Hero");

        addAttack(new OnlyDamage(7, 1));
    }

    public void tactics() {}

}
