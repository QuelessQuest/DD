package com.barrypress.dd.core.monster;

public class OnlyDamage extends Attack {

    public OnlyDamage(String name, int attack, int damage) {
        super();
        setAttack(attack);
        setDamage(damage);
        setName(name);
    }

    public void special() {}
}
