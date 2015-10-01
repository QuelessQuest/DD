package com.barrypress.dd.core.character;


import com.barrypress.dd.core.character.power.Power;

public class Allisa extends PC {

    public Allisa() {
        init();
        setAc(16);
        setMaxHp(10);
        setHp(10);
        setSpeed(6);
        setSurge(5);
        setName("Allisa");
        setTag("allisa");
        setRace("Human");
        setRole("Ranger");
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