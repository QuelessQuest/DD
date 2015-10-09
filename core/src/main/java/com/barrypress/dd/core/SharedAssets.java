package com.barrypress.dd.core;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class SharedAssets {

    private List<PC> characters;
    private List<Monster> monsters;
    private Label mName;
    private Label mAC;
    private Label mHP;
    private Label mXP;
    private Skin skin;

    private Table monsterTactics = new Table();
    private Table monsterAttacks = new Table();

    public SharedAssets(Skin skin) {
        this.skin = skin;
        characters = new ArrayList<>();
        monsters = new ArrayList<>();
        mName = new Label("Boo", skin);
        mAC = new Label("0", skin);
        mHP = new Label("0", skin);
        mXP = new Label("0", skin);

        monsterTactics = new Table();
        monsterAttacks = new Table();
    }

    public List<PC> getCharacters() {
        return characters;
    }

    public void setCharacters(List<PC> characters) {
        this.characters = characters;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public Label getmName() {
        return mName;
    }

    public void setmName(Label mName) {
        this.mName = mName;
    }

    public Label getmAC() {
        return mAC;
    }

    public void setmAC(Label mAC) {
        this.mAC = mAC;
    }

    public Label getmHP() {
        return mHP;
    }

    public void setmHP(Label mHP) {
        this.mHP = mHP;
    }

    public Label getmXP() {
        return mXP;
    }

    public void setmXP(Label mXP) {
        this.mXP = mXP;
    }

    public Table getMonsterTactics() {
        return monsterTactics;
    }

    public void setMonsterTactics(Table monsterTactics) {
        this.monsterTactics = monsterTactics;
    }

    public Table getMonsterAttacks() {
        return monsterAttacks;
    }

    public void setMonsterAttacks(Table monsterAttacks) {
        this.monsterAttacks = monsterAttacks;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
