package com.barrypress.dd.core.character.power;

import com.barrypress.dd.core.character.PC;

public class HealingWord extends Power {

    public HealingWord() {
        super();
        setPowerType(PowerType.UTILITY);
        setName("Healing Word");
        setTag("healing_word");
    }

    public void activate(PC pc) {

    }
}
