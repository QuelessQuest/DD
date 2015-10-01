package com.barrypress.dd.core.character;

public class Buff {

    public enum BuffType {AC, HP, SPEED, DISABLE, ATTACK, DAMAGE};

    private BuffType type;
    private Integer amount;
    private Integer duration;

    public Buff(BuffType type, Integer amount, Integer duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    public BuffType getType() {
        return type;
    }

    public void setType(BuffType type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}