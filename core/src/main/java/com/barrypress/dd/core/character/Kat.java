package com.barrypress.dd.core.character;


import com.barrypress.dd.core.character.power.Power;
import com.barrypress.dd.core.character.power.SneakAttack;

public class Kat extends PC {

    public Kat() {
        init();
        setAc(14);
        setMaxHp(8);
        setHp(8);
        setSpeed(6);
        setSurge(4);
        setName("Kat");
        setTag("kat");
        setRace("Human");
        setRole("Rogue");
        getKnownPowers().add(new SneakAttack());
        addStartingPower(Power.PowerType.UTILITY);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.DAILY);
        getBuffs().add(new Buff(Buff.BuffType.DISABLE, 5, 99999));
    }

    public void startExplorationPhase() {}

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