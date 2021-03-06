package com.barrypress.dd.core.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.barrypress.dd.core.Piece;
import com.barrypress.dd.core.character.power.Power;

import java.util.ArrayList;
import java.util.List;

public abstract class PC extends Piece {


    private Integer maxHp;
    private Integer speed;
    private Integer surge;
    private Integer level;
    private String race;
    private String role;
    private String tag;

    private Color color;
    private Label hpLabel;
    private Label acLabel;
    private Table table;
    private TextureMapObject mapObject;

    private boolean slowed;
    private boolean immobilized;
    private boolean attacked;
    private boolean movedOne;
    private boolean movedTwo;

    private List<Power> utilityPowers;
    private List<Power> dailyPowers;
    private List<Power> atWillPowers;
    private List<Power> knownPowers;
    private List<Buff> buffs;
    private List<Power.PowerType> startingPowerTypes;

    public void PC() {
    }

    public void init(Skin skin) {
        super.init();
        utilityPowers = new ArrayList<>();
        dailyPowers = new ArrayList<>();
        atWillPowers = new ArrayList<>();
        knownPowers = new ArrayList<>();
        buffs = new ArrayList<>();
        startingPowerTypes = new ArrayList<>();
        table = new Table();
        table.setSkin(skin);
        hpLabel = new Label("0", skin);
        hpLabel.setAlignment(Align.center);
        acLabel = new Label("0", skin);
        acLabel.setAlignment(Align.center);
        slowed = false;
        immobilized = false;
        attacked = false;
        level = 1;
        movedOne = false;
        movedTwo = false;
    }

    public abstract void endHeroPhaseSpecial();
    public abstract void levelUp();
    public abstract Buff tileBuff();
    public abstract void startExplorationPhase();

    protected void updateTable(Sprite me) {
        getTable().background(new SpriteDrawable(me));
        getTable().add("").colspan(3).height(55f).width(100f);
        getTable().row();
        getTable().bottom().left();
        getTable().add(getAcLabel()).width(40f).height(45f).top().left();
        getTable().add("").width(10f);
        getTable().add(getHpLabel()).width(50f).height(45f).top().left();
    }

    public void endHeroPhase() {
        slowed = false;
        immobilized = false;

        List<Buff> removals = new ArrayList<>();
        for (Buff buff : buffs) {
            Integer duration = buff.getDuration();
            duration -= 1;
            if (duration.compareTo(0) <= 0) {
                removals.add(buff);
            } else {
                buff.setDuration(duration);
            }
        }
        for (Buff buff : removals) {
            buffs.remove(buff);
        }
    }

    public void clearConditions() {
        slowed = false;
        immobilized = false;
    }


    public void addBuff(Buff.BuffType type, Integer amount, Integer duration) {
        buffs.add(new Buff(type, amount, duration));
    }

    public void startHeroPhase() {

    }

    public void usePower(Power power) {
        power.activate(this);
    }

    public Integer takeDamage(Integer damage) {
        setHp(getHp() - damage);
        return getHp();
    }

    public boolean isDead() {
        return getHp().compareTo(0) <= 0;
    }

    public boolean isHit(Integer roll) {
        return roll.compareTo(getAc()) >= 0;
    }

    public Integer getBuffs(Buff.BuffType type) {

        Integer bonus = 0;

        for (Buff buff : buffs) {
            if (buff.getType().equals(type)) {
                bonus += buff.getAmount();
            }
        }

        return bonus;
    }

    public void heal(Integer amount) {
        setHp(getHp() + amount);
    }

    @Override
    public Integer getAc() {
        Integer tmpAc = super.getAc();
        if (!buffs.isEmpty()) {
            for (Buff buff : buffs) {
                if (buff.getType() == Buff.BuffType.AC) {
                    tmpAc += buff.getAmount();
                }
            }
        }
        return tmpAc;
    }

    public boolean isMovedOne() {
        return movedOne;
    }

    public boolean isMovedTwo() {
        return movedTwo;
    }

    public void moved() {
        if (!movedOne)
            movedOne = true;
        else if (!movedTwo) {
            movedTwo = true;
        }
    }

    @Override
    public void setAc(Integer ac) {
        super.setAc(ac);
        acLabel.setText(ac.toString());
    }

    @Override
    public Integer getHp() {
        Integer tmpHp = super.getHp();
        if (!buffs.isEmpty()) {
            for (Buff buff : buffs) {
                if (buff.getType() == Buff.BuffType.HP) {
                    tmpHp += buff.getAmount();
                }
            }
        }
        return tmpHp;
    }

    @Override
    public void setHp(Integer hp) {
        super.setHp(hp);
        if (super.getHp().compareTo(maxHp) > 0) {
            super.setHp(maxHp);
        }
        hpLabel.setText(super.getHp().toString());
    }

    public Integer getSpeed() {
        Integer tmpSpeed = speed;
        if (slowed) tmpSpeed -= 2;
        if (immobilized) tmpSpeed = 0;
        if (!buffs.isEmpty()) {
            for (Buff buff : buffs) {
                if (buff.getType() == Buff.BuffType.SPEED) {
                    tmpSpeed += buff.getAmount();
                }
            }
        }
        return tmpSpeed;
    }

    public void addStartingPower(Power.PowerType powerType) {
        startingPowerTypes.add(powerType);
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getSurge() {
        return surge;
    }

    public void setSurge(Integer surge) {
        this.surge = surge;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Power> getUtilityPowers() {
        return utilityPowers;
    }

    public void setUtilityPowers(List<Power> utilityPowers) {
        this.utilityPowers = utilityPowers;
    }

    public List<Power> getDailyPowers() {
        return dailyPowers;
    }

    public void setDailyPowers(List<Power> dailyPowers) {
        this.dailyPowers = dailyPowers;
    }

    public List<Power> getAtWillPowers() {
        return atWillPowers;
    }

    public void setAtWillPowers(List<Power> atWillPowers) {
        this.atWillPowers = atWillPowers;
    }

    public List<Power> getKnownPowers() {
        return knownPowers;
    }

    public void setKnownPowers(List<Power> knownPowers) {
        this.knownPowers = knownPowers;
    }

    public boolean isSlowed() {
        return slowed;
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    public boolean isImmobilized() {
        return immobilized;
    }

    public void setImmobilized(boolean immobilized) {
        this.immobilized = immobilized;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public boolean hasAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Label getHpLabel() {
        return hpLabel;
    }

    public void setHpLabel(Label hpLabel) {
        this.hpLabel = hpLabel;
    }

    public Label getAcLabel() {
        return acLabel;
    }

    public void setAcLabel(Label acLabel) {
        this.acLabel = acLabel;
    }

    public TextureMapObject getMapObject() {
        return mapObject;
    }

    public void setMapObject(TextureMapObject mapObject) {
        this.mapObject = mapObject;
    }
}
