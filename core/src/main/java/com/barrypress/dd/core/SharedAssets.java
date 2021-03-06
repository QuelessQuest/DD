package com.barrypress.dd.core;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.barrypress.dd.core.board.BoardTile;
import com.barrypress.dd.core.board.BoardView;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.hud.FlatScreen;
import com.barrypress.dd.core.hud.HUDView;
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
    private Label mpAC;
    private Label mpHP;
    private Skin skin;
    private Skin smallSkin;
    private float mWidth;
    private Phase phase;
    private TiledMap map;

    private boolean boardViewVisible;
    private InputMultiplexer inputMultiplexer;
    private BoardView boardView;
    private HUDView hudView;
    private FlatScreen screen;
    private TextArea monsterTactics;
    private TextArea logArea;
    private Table monsterAttacks;
    private Table monsterPortrait;

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
        boardView = null;
        boardViewVisible = false;

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

    public Table getMonsterPortrait() {
        return monsterPortrait;
    }

    public void setMonsterPortrait(Table monsterPortrait) {
        this.monsterPortrait = monsterPortrait;
    }

    public Label getMpAC() {
        return mpAC;
    }

    public void setMpAC(Label mpAC) {
        this.mpAC = mpAC;
    }

    public Label getMpHP() {
        return mpHP;
    }

    public void setMpHP(Label mpHP) {
        this.mpHP = mpHP;
    }

    public TextArea getLogArea() {
        return logArea;
    }

    public void setLogArea(TextArea logArea) {
        this.logArea = logArea;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public FlatScreen getScreen() {
        return screen;
    }

    public void setScreen(FlatScreen screen) {
        this.screen = screen;
    }

    public HUDView getHudView() {
        return hudView;
    }

    public void setHudView(HUDView hudView) {
        this.hudView = hudView;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public void setInputMultiplexer(InputMultiplexer inputMultiplexer) {
        this.inputMultiplexer = inputMultiplexer;
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public boolean isBoardViewVisible() {
        return boardViewVisible;
    }

    public void setBoardViewVisible(boolean boardViewVisible) {
        this.boardViewVisible = boardViewVisible;
    }
}
