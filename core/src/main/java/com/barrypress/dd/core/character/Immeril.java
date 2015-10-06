package com.barrypress.dd.core.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.barrypress.dd.core.character.power.FeyStep;
import com.barrypress.dd.core.character.power.Power;

public class Immeril extends PC {

    public Immeril(TextureAtlas spriteSheet, Skin skin) {
        init(skin);
        setAc(14);
        setMaxHp(6);
        setHp(6);
        setSpeed(6);
        setSurge(3);
        setName("Immeril");
        setTag("immeril");
        setRace("Eladrin");
        setRole("Wizard");
        setColor(new Color(43/255f, 35/255f, 24/255f, 1f));
        updateTable(new Sprite(spriteSheet.findRegion(getTag())));
        getKnownPowers().add(new FeyStep());
        addStartingPower(Power.PowerType.UTILITY);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.DAILY);
    }

    public void startExplorationPhase() {}

    public Buff tileBuff() {
        return new Buff(Buff.BuffType.ATTACK, 1, 1);
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