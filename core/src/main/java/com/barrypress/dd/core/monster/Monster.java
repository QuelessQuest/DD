package com.barrypress.dd.core.monster;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.character.PC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Monster extends Piece {

    private String type;
    private int xp;
    private PC owner;

    private Map<String, String> tactics;
    private List<Attack> attacks;

    public Monster() {}

    public void init() {
        super.init();
        tactics = new HashMap<>();
        attacks = new ArrayList<>();
    }

    public abstract void tactics();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Map<String, String> getTactics() {
        return tactics;
    }

    public void setTactics(Map<String, String> tactics) {
        this.tactics = tactics;
    }

    public void addTactic(String bold, String description) {
        tactics.put(bold, description);
    }

    public void addAttack(Attack attack) {
        attacks.add(attack);
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public PC getOwner() {
        return owner;
    }

    public void setOwner(PC owner) {
        this.owner = owner;
    }

    @Override
    public Sprite getDrawSprite() { return getSprite(); }
}
