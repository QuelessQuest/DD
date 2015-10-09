package com.barrypress.dd.core.monster;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.character.PC;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Monster extends Piece {

    private String type;
    private Integer xp;
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

    public void updateTactics(Table table, Skin skin) {

        table.clear();
        for (Map.Entry<String, String> tactic : tactics.entrySet()) {
            table.add(new Label(tactic.getKey(), skin));
            table.add(new Label(tactic.getValue(), skin));
            table.row();
        }

    }

    public void updateAttacks(Table table, Skin skin) {

        table.clear();
        for (Attack attack : attacks) {
            table.add(new Label(attack.getName(), skin));
            table.add(new Label(attack.getAttack().toString(), skin));
            table.add(new Label(attack.getDamage().toString(), skin));
            if (StringUtils.isNotEmpty(attack.getSpecialDescription())) {
                table.row();
                table.add(new Label(attack.getSpecialDescription(), skin)).colspan(3);
            }
            table.row();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
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
