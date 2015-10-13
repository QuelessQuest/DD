package com.barrypress.dd.core.monster;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
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

    private SpriteDrawable portrait;

    public Monster() {}

    public void init() {
        super.init();
        tactics = new HashMap<>();
        attacks = new ArrayList<>();
    }

    public abstract String tactics(TiledMap map, List<PC> characters, List<Monster> monsters);

    public void updateTactics(TextArea text, Skin skin) {

        text.setText("");
        for (Map.Entry<String, String> tactic : tactics.entrySet()) {
            text.appendText(tactic.getKey());
            text.appendText(tactic.getValue());
        }

    }

    public void updateAttacks(Table table, Skin skin, float width) {

        table.clearChildren();
        Label title = new Label("ATTACK", skin);
        title.setAlignment(Align.center);
        Label titleHit = new Label("HIT", skin);
        titleHit.setAlignment(Align.center);
        Label titleDmg = new Label("DAMAGE", skin);
        titleDmg.setAlignment(Align.center);
        table.add(title).width(width * .55f);
        table.add(titleHit).width(width * .20f);
        table.add(titleDmg).width(width * .25f);
        table.row();
        for (Attack attack : attacks) {
            Label name = new Label(attack.getName(), skin);
            name.setAlignment(Align.center);
            table.add(name).width(width * .55f);
            String strAttack;
            if (attack.getAttack() > 0) {
                strAttack = "+" + attack.getAttack().toString();
            } else {
                strAttack = attack.getAttack().toString();
            }
            Label attackValue = new Label(strAttack, skin);
            attackValue.setAlignment(Align.center);
            table.add(attackValue).width(width * .20f);
            Label dmgValue = new Label(attack.getDamage().toString(), skin);
            dmgValue.setAlignment(Align.center);
            table.add(dmgValue).width(width * .25f);
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

    public SpriteDrawable getPortrait() {
        return portrait;
    }

    public void setPortrait(SpriteDrawable portrait) {
        this.portrait = portrait;
    }
}
