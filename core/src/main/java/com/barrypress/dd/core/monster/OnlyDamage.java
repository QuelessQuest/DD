package com.barrypress.dd.core.monster;

public class OnlyDamage extends Attack {

    public OnlyDamage(int attack, int damage) {
        super();
        setAttack(attack);
        setDamage(damage);
        setName("Attack");
    }

    public void special() {}
}
