package com.barrypress.dd.core.character;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.barrypress.dd.core.character.power.DragonsBreath;
import com.barrypress.dd.core.character.power.Power;

public class Arjhan extends PC {

    public Arjhan(TextureAtlas spriteSheet, Skin skin) {
        init(skin);
        setAc(17);
        setHp(10);
        setMaxHp(10);
        setSpeed(5);
        setSurge(5);
        setName("Arjhan");
        setRace("Dragonborn");
        setRole("Fighter");
        updateTable(new Sprite(spriteSheet.findRegion(getTag())));
        getKnownPowers().add(new DragonsBreath());
        addStartingPower(Power.PowerType.UTILITY);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.DAILY);
    }

    public void startExplorationPhase() {}

    public Buff tileBuff() {
        return new Buff(Buff.BuffType.AC, 1, 1);
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