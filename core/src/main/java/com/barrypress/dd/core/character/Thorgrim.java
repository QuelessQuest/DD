package com.barrypress.dd.core.character;

import com.barrypress.dd.core.character.power.HealingWord;
import com.barrypress.dd.core.character.power.Power;

import java.util.ArrayList;
import java.util.List;

public class Thorgrim extends PC {

    public Thorgrim() {
        init();
        setAc(16);
        setMaxHp(8);
        setHp(8);
        setSpeed(5);
        setSurge(4);
        setName("Thorgrim");
        setTag("thorgrim");
        setRace("Dwarf");
        setRole("Cleric");
        getKnownPowers().add(new HealingWord());
        addStartingPower(Power.PowerType.UTILITY);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.AT_WILL);
        addStartingPower(Power.PowerType.DAILY);
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

        if (!hasAttacked()) {
            // TODO call function to get all other PCs on same tile
            String msg = "Thorgrim may now AID any other Hero on his current tile, healing them 1 Hit Point.";
            List<PC> pcs = new ArrayList<>();

            if (!pcs.isEmpty()) {
                // TODO call function to allow selection of a PC
                PC pc = pcs.get(0);
                pc.heal(1);
            }
        }
    }
}