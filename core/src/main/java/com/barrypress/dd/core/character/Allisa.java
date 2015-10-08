package com.barrypress.dd.core.character;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.barrypress.dd.core.character.power.Power;

public class Allisa extends PC {

    public Allisa(TextureAtlas spriteSheet, Skin skin) {
        init(skin);
        setAc(16);
        setMaxHp(10);
        setHp(10);
        setSpeed(6);
        setSurge(5);
        setName("Allisa");
        setTag("allisa");
        setRace("Human");
        setRole("Ranger");
        setColor(new Color(71/255f, 85/255f, 23/255f, .5f));
        updateTable(new Sprite(spriteSheet.findRegion(getTag())));
        setSprite(new Sprite(spriteSheet.findRegion("m_" + getTag())));
        setHighlightSprite(new Sprite(spriteSheet.findRegion("g_" + getTag())));
        setOffsetX(-5f);
        addStartingPower(Power.PowerType.UTILITY);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.DAILY);
    }

    public void startExplorationPhase() {
        // TODO Explore tile
    }

    public Buff tileBuff() {
        // None
        return null;
    }

    public void levelUp() {
        setMaxHp(getMaxHp() + 2);
        setAc(getAc() + 1);
        setSurge(getSurge() + 1);
        setLevel(getLevel() + 1);

        // TODO choose Daily Power
    }

    public void endHeroPhaseSpecial() {
        return;
    }
}