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
import com.barrypress.dd.core.character.PC;
import javafx.scene.PerspectiveCamera;

import java.util.List;

public class HUDView {

    private AssetManager assetManager;
    private BitmapFont bitmapFont;
    private Label labelAC;
    private Label labelHP;
    private Label labelSPD;
    private Label labelSRG;
    private Label surges;
    private Label name;
    private PerspectiveCamera camera;
    private Skin ddSkin;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Table character;
    private TextureAtlas spriteSheet;

    private List<PC> characters;

    public HUDView(List<PC> characters, AssetManager assetManager, TextureAtlas spriteSheet, Skin skin) {
        this.assetManager = assetManager;
        this.characters = characters;
        this.skin = skin;
        this.spriteSheet = spriteSheet;
    }

    public void init() {

        Gdx.app.debug("HUDView", "Initializing...");

        float height = (float) Gdx.graphics.getHeight();
        float width  = (float) Gdx.graphics.getWidth();

        bitmapFont = new BitmapFont();

        ddSkin = new Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/text.json"));

        batch = new SpriteBatch();
        camera = new PerspectiveCamera();

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
        character = new Table();
        Table characterSkills = new Table();

        leftSide.setSkin(skin);
        rightSide.setSkin(skin);
        portraits.setSkin(skin);
        character.setSkin(skin);
        characterSkills.setSkin(skin);
        nameTable.setSkin(skin);

        labelAC = new Label("0", ddSkin);
        labelHP = new Label("0", ddSkin);
        labelSPD = new Label("0", ddSkin);
        labelSRG = new Label("0", ddSkin);
        Table characterInfo = new Table();
        characterInfo.setSkin(ddSkin);
        characterInfo.top().left();
        float cWidth = width * .79f;
        float ntWidth = cWidth * .24f;
        float ntHeight = height * .28f;
        float ciHeight = ntHeight * .86f;

        characterInfo.add("").height(ciHeight * .22f).width(ntWidth * .1f);
        characterInfo.add("ac").height(ciHeight * .22f).width(ntWidth * .35f);
        characterInfo.add(labelAC).center().height(ciHeight * .25f).width(ntWidth * .55f).bottom();
        characterInfo.row();
        characterInfo.add("").height(ciHeight * .22f).width(ntWidth * .1f);
        characterInfo.add("hp").height(ciHeight * .22f).width(ntWidth * .35f);
        characterInfo.add(labelHP).center().height(ciHeight * .22f).width(ntWidth * .55f);
        characterInfo.row();
        characterInfo.add("").height(ciHeight * .22f).width(ntWidth * .1f);
        characterInfo.add("speed").height(ciHeight * .22f).width(ntWidth * .35f);
        characterInfo.add(labelSPD).center().height(ciHeight * .19f).width(ntWidth * .55f);
        characterInfo.row();
        characterInfo.add("").height(ciHeight * .22f).width(ntWidth * .1f);
        characterInfo.add("surge").height(ciHeight * .22f).width(ntWidth * .35f);
        characterInfo.add(labelSRG).center().height(ciHeight * .18f).width(ntWidth * .55f);
        characterInfo.row();
        characterInfo.add("").height(ciHeight * .12f).colspan(3);

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

        name = new Label("Name", skin);
        name.setAlignment(Align.center);

        nameTable.top().left();
        nameTable.add("").width(ntWidth * .1f).height(ntHeight * .14f);
        nameTable.add(name).width(ntWidth * .8f);
        nameTable.add("").width(ntWidth * .1f);
        nameTable.row();
        nameTable.add(characterInfo).colspan(3).height(ntHeight * .86f);

        character.add("").width(cWidth * .02f);
        character.add(nameTable).width(cWidth * .24f).height(height * .28f).top().left();
        character.add("").width(cWidth * .03f);
        character.add(characterSkills).width(cWidth * .435f);
        character.add("").width(cWidth * .035f);
        character.add("details").top().right().width(cWidth * .24f);

        portraits.left().top();
        boolean flag = true;
        for (PC pc : characters) {
            if (flag) {
                portraits.add(pc.getTable()).bottom().left();
                portraits.add(new Image(spriteSheet.findRegion("blank_circle"))).expandX().top().right();
                flag = false;
            } else {
                portraits.add(pc.getTable()).colspan(2).bottom().left();
            }
            PCListener pcListener = new PCListener();
            pcListener.init(characters, pc, name, labelAC, labelHP, labelSPD, labelSRG);
            pc.setListener(pcListener);
            pc.getTable().addListener(pc.getListener());
            portraits.row();
        }

        leftSide.add(portraits).width(width * .79f).height(height * .69f);
        leftSide.row();
        leftSide.add(character).width(width * .79f).top().left();

        TextArea textArea = new TextArea("This is where stuff goes", skin);
        textArea.setPrefRows(5.5f);
        surges = new Label("Surges Remaining:  0", skin);
        surges.setAlignment(Align.center);
        float rWidth = width * .21f;
        rightSide.add("Monster").colspan(3).height(height * .06f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .23f);
        rightSide.add("mdetail").width(rWidth * .81f).top().left();
        rightSide.add("").width(rWidth * .06f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .04f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .25f);
        rightSide.add("state").width(rWidth * .81f).top().left();
        rightSide.add("").width(rWidth * .04f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .04f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .045f);
        rightSide.add(surges).width(rWidth * .81f).height(height * .045f);
        rightSide.add("").width(rWidth * .06f);
        rightSide.row();
        rightSide.add("").colspan(3).height(height * .04f);
        rightSide.row();
        rightSide.add("").width(rWidth * .13f).height(height * .18f);
        rightSide.add(textArea).width(rWidth * .81f).top().left();
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

        //Gdx.gl20.glClearColor(47/255f, 47/255f, 47/255f, 0F);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage.setDebugAll(true);
        stage.act();
        stage.draw();
    }

    public Stage getStage() { return stage; }

}
