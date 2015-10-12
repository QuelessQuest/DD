package com.barrypress.dd.core;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.barrypress.dd.core.board.BoardTile;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class SharedAssets {

    public enum Phase { HERO, VILLAIN };

    private List<PC> characters;
    private List<Monster> monsters;
    private Label mName;
    private Label mAC;
    private Label mHP;
    private Label mXP;
    private Skin skin;
    private Skin smallSkin;
    private float mWidth;
    private Phase phase;

    private TextArea monsterTactics;
    private Table monsterAttacks;

    public SharedAssets(Skin skin, Skin smallSkin) {
        this.smallSkin = smallSkin;
        this.skin = skin;
        characters = new ArrayList<>();
        monsters = new ArrayList<>();
        mName = new Label("Boo", skin);
        mAC = new Label("0", skin);
        mHP = new Label("0", skin);
        mXP = new Label("0", skin);
        phase = Phase.HERO;

        monsterTactics = new TextArea("Text Area", smallSkin);
        monsterAttacks = new Table();
        monsterAttacks.setSkin(smallSkin);
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

    public TextArea getMonsterTactics() {
        return monsterTactics;
    }

    public void setMonsterTactics(TextArea monsterTactics) {
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

    public Skin getSmallSkin() {
        return smallSkin;
    }

    public void setSmallSkin(Skin smallSkin) {
        this.smallSkin = smallSkin;
    }

    public float getmWidth() {
        return mWidth;
    }

    public void setmWidth(float mWidth) {
        this.mWidth = mWidth;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
