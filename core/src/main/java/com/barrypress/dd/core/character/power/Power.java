package com.barrypress.dd.core.character.power;


import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.utility.Constants;

public abstract class Power {

    public enum PowerType {UTILITY, AT_WILL, DAILY};

    private PowerType powerType;
    private boolean used;
    private String name;
    private String tag;
    private String description;
    private Constants.Phase phase;
    private Constants.Role role;

    public Power() {
        used = false;
    }

    public abstract void activate(PC pc);

    public PowerType getPowerType() {
        return powerType;
    }

    public void setPowerType(PowerType powerType) {
        this.powerType = powerType;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Constants.Phase getPhase() {
        return phase;
    }

    public void setPhase(Constants.Phase phase) {
        this.phase = phase;
    }

    public Constants.Role getRole() {
        return role;
    }

    public void setRole(Constants.Role role) {
        this.role = role;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
