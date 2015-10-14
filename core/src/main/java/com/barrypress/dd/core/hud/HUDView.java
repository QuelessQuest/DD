package com.barrypress.dd.core.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.barrypress.dd.core.SharedAssets;
import com.barrypress.dd.core.character.PC;
import com.barrypress.dd.core.monster.Monster;
import com.barrypress.dd.core.monster.MonsterListener;

import java.util.List;

public class HUDView {

    private AssetManager assetManager;
    private Skin skin;
    private Skin smallSkin;
    private Stage stage;
    private TextureAtlas spriteSheet;
    private SharedAssets sharedAssets;

    private List<PC> characters;
    private List<Monster> monsters;

    public HUDView(SharedAssets sharedAssets, AssetManager assetManager, TextureAtlas spriteSheet) {
        this.assetManager = assetManager;
        this.sharedAssets = sharedAssets;
        characters = sharedAssets.getCharacters();
        monsters = sharedAssets.getMonsters();
        this.skin = sharedAssets.getSkin();
        this.smallSkin = sharedAssets.getSmallSkin();
        this.spriteSheet = spriteSheet;
    }

    public void init() {

        Gdx.app.debug("HUDView", "Initializing...");

        float height = (float) Gdx.graphics.getHeight();
        float width  = (float) Gdx.graphics.getWidth();

        SpriteBatch batch = new SpriteBatch();

        NinePatch background = new NinePatch(spriteSheet.findRegion("background"), 10, 10, 10, 10);
        skin.add("background", background);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        stage = new Stage(new StretchViewport((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight()), batch);

        Sprite mainGame = new Sprite(new Texture(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/maingame.png")));

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setSkin(skin);
        mainTable.setBackground(new SpriteDrawable(mainGame));
        mainTable.left().top();

        Table leftSide = new Table();
        Table rightSide = new Table();
        Table portraits = new Table();
        Table nameTable = new Table();
        Table character = new Table();
        Table characterSkills = new Table();

        leftSide.setSkin(skin);
        rightSide.setSkin(skin);
        portraits.setSkin(skin);
        character.setSkin(skin);
        characterSkills.setSkin(skin);
        nameTable.setSkin(skin);

        Label labelAC = new Label("0", skin);
        Label labelHP = new Label("0", skin);
        Label labelSPD = new Label("0", skin);
        Label labelSRG = new Label("0", skin);
        Table characterInfo = new Table();
        characterInfo.setSkin(skin);
        characterInfo.bottom().left();
        float cWidth = width * .79f;
        float ntWidth = cWidth * .24f;
        float ntHeight = height * .28f;
        float ciHeight = ntHeight * .86f;

        labelAC.setAlignment(Align.bottomLeft);
        labelHP.setAlignment(Align.bottomLeft);
        labelSPD.setAlignment(Align.bottomLeft);
        labelSRG.setAlignment(Align.bottomLeft);

        Label labelACTitle = new Label("AC", skin);
        Label labelHPTitle = new Label("HP", skin);
        Label labelSPDTitle = new Label("Speed", skin);
        Label labelSRGTitle = new Label("Surge", skin);

        labelACTitle.setAlignment(Align.bottomLeft);
        labelHPTitle.setAlignment(Align.bottomLeft);
        labelSPDTitle.setAlignment(Align.bottomLeft);
        labelSRGTitle.setAlignment(Align.bottomLeft);

        characterInfo.add("").height(ciHeight * .20f).width(ntWidth * .10f);
        characterInfo.add(labelACTitle).bottom().height(ciHeight * .20f).width(ntWidth * .225f);
        characterInfo.add(labelAC).bottom().height(ciHeight * .20f).width(ntWidth * .225f);
        characterInfo.add(labelHPTitle).bottom().height(ciHeight * .20f).width(ntWidth * .225f);
        characterInfo.add(labelHP).bottom().height(ciHeight * .20f).width(ntWidth * .225f);
        characterInfo.row();
        characterInfo.add("").height(ciHeight * .15f).width(ntWidth * .10f);
        characterInfo.add(labelSPDTitle).height(ciHeight * .15f).width(ntWidth * .225f);
        characterInfo.add(labelSPD).center().height(ciHeight * .15f).width(ntWidth * .225f);
        characterInfo.add(labelSRGTitle).height(ciHeight * .15f).width(ntWidth * .225f);
        characterInfo.add(labelSRG).center().height(ciHeight * .15f).width(ntWidth * .225f);
        characterInfo.row();
        characterInfo.add("").height(ciHeight * .65f).colspan(3);

        characterSkills.add("At Will");
        characterSkills.add(new Image(spriteSheet.findRegion("power")));
        characterSkills.add("AW2");
        characterSkills.row();
        characterSkills.add("Daily");
        characterSkills.add("D1");
        characterSkills.add("D2");
        characterSkills.row();
        characterSkills.add("Utility");
        characterSkills.add("U1");
        characterSkills.add("U2");
        characterSkills.row();
        characterSkills.add("Item");
        characterSkills.add("I1");
        characterSkills.add("I2");

        Label name = new Label("Name", skin);
        name.setAlignment(Align.center);

        nameTable.top().left();
        nameTable.add("").width(ntWidth * .1f).height(ntHeight * .14f);
        nameTable.add(name).width(ntWidth * .8f);
        nameTable.add("").width(ntWidth * .1f);
        nameTable.row();
        nameTable.add(characterInfo).colspan(3).height(ntHeight * .86f);

        Table details = new Table();
        details.setSkin(skin);
        details.add("Power/Item Detail").center().height(ntHeight * .14f);
        details.row();
        details.add("").height(ntHeight * .05f);
        details.row();
        TextArea detailText = new TextArea("", smallSkin);
        detailText.setPrefRows(7.5f);
        details.add(detailText);

        character.add("").width(cWidth * .02f);
        character.add(nameTable).width(cWidth * .24f).height(height * .28f).top().left();
        character.add("").width(cWidth * .03f);
        character.add(characterSkills).width(cWidth * .435f);
        character.add("").width(cWidth * .035f);
        character.add(details).top().right().width(cWidth * .24f);

        Table monsterPortrait = new Table();
        monsterPortrait.setSkin(skin);
        Label monsterAC = new Label("0", skin);
        monsterAC.setAlignment(Align.center);
        Label monsterHP = new Label("0", skin);
        monsterHP.setAlignment(Align.center);
        monsterPortrait.background(new SpriteDrawable(new Sprite(spriteSheet.findRegion("blank_circle"))));
        monsterPortrait.add("").colspan(3).height(55f).width(100f);
        monsterPortrait.row();
        monsterPortrait.bottom().left();
        monsterPortrait.add(monsterAC).width(40f).height(45f).top().left();
        monsterPortrait.add("").width(10f);
        monsterPortrait.add(monsterHP).width(50f).height(45f).top().left();

        sharedAssets.setMonsterPortrait(monsterPortrait);
        sharedAssets.setMpAC(monsterAC);
        sharedAssets.setMpHP(monsterHP);

        portraits.left().top();
        boolean flag = true;
        for (PC pc : characters) {
            if (flag) {
                portraits.add(pc.getTable()).bottom().left();
                portraits.add(monsterPortrait).expandX().top().right();
                flag = false;
            } else {
                portraits.add(pc.getTable()).colspan(2).bottom().left();
            }
            PCListener pcListener = new PCListener();
            pcListener.init(sharedAssets, characters, pc, name, labelAC, labelHP, labelSPD, labelSRG);
            pc.setListener(pcListener);
            pc.getTable().addListener(pc.getListener());
            portraits.row();
        }

        leftSide.add(portraits).width(width * .79f).height(height * .69f);
        leftSide.row();
        leftSide.add(character).width(width * .79f).top().left();

        Table monsterTable = new Table();
        monsterTable.setSkin(skin);
        monsterTable.top().left();

        Label labelACTitle2 = new Label("AC", skin);
        Label labelHPTitle2 = new Label("HP", skin);
        Label labelXPTitle = new Label("XP", skin);
        labelXPTitle.setAlignment(Align.bottomLeft);
        labelACTitle2.setAlignment(Align.bottomLeft);
        labelHPTitle2.setAlignment(Align.bottomLeft);

        float rWidth = width * .21f;
        float mWidth = rWidth * .81f;
        float mHeight = height * .23f;

        sharedAssets.setmWidth(mWidth * .90f);
        monsterTable.add("").colspan(6).height(mHeight * .05f);
        monsterTable.row();
        monsterTable.add(labelACTitle2).width(mWidth * .16f);
        monsterTable.add(sharedAssets.getmAC()).width(mWidth * .16f);
        monsterTable.add(labelHPTitle2).width(mWidth * .16f);
        monsterTable.add(sharedAssets.getmHP()).width(mWidth * .16f);
        monsterTable.add(labelXPTitle).width(mWidth * .16f);
        monsterTable.add(sharedAssets.getmXP()).width(mWidth * .20f);
        monsterTable.row();
        monsterTable.add("").colspan(6).height(mHeight * .05f);
        monsterTable.row();
        monsterTable.add(sharedAssets.getMonsterTactics()).colspan(6).height(mHeight * .60f).width(mWidth);
        monsterTable.row();
        monsterTable.add(sharedAssets.getMonsterAttacks()).colspan(6).width(mWidth * .90f).expandY().left();

        MonsterListener monsterListener = new MonsterListener();
        monsterListener.init(monsters, monsters.get(0), sharedAssets.getmName(), sharedAssets.getmAC(), sharedAssets.getmHP());
        monsters.get(0).setListener(monsterListener);

        TextArea logArea = new TextArea("", smallSkin);
        logArea.setPrefRows(7.5f);
        sharedAssets.setLogArea(logArea);
        rightSide.add(sharedAssets.getmName()).colspan(3).height(height * .06f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .23f);
        rightSide.add(monsterTable).width(rWidth * .81f).height(height * .52f).top().left();
        rightSide.add("").width(rWidth * .06f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .025f);
        rightSide.row();
        Label logLabel = new Label("Log", skin);
        logLabel.setAlignment(Align.center);
        rightSide.add(logLabel).colspan(3).height(height * .045f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .015f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .22f);
        rightSide.add(logArea).width(rWidth * .81f).top().left();
        rightSide.add("").width(rWidth * .06f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .04f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .04f);
        rightSide.add("toolbar").width(rWidth * .81f);
        rightSide.add("").width(rWidth * .06f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .055f);

        mainTable.add(leftSide).top().left();
        mainTable.add(rightSide).width(width * .21f).top().left();

        stage.addActor(mainTable);
    }

    public void render() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage.setDebugAll(true);
        stage.act();
        stage.draw();
    }

    public Stage getStage() { return stage; }

}