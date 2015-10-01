package com.barrypress.dd.core.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.barrypress.dd.core.character.PC;
import javafx.scene.PerspectiveCamera;

import java.util.List;

public class HUDView {

    private AssetManager assetManager;
    private BitmapFont bitmapFont;
    private PerspectiveCamera camera;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private TextureAtlas spriteSheet;

    private List<PC> characters;

    public HUDView(List<PC> characters) {
        this.characters = characters;
    }

    public void init() {

        Gdx.app.debug("HUDView", "Initializing...");

        float height = (float) Gdx.graphics.getHeight();
        float width  = (float) Gdx.graphics.getWidth();

        assetManager = new AssetManager();
        assetManager.load("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt", TextureAtlas.class);
        assetManager.finishLoading();

        bitmapFont = new BitmapFont();

        this.skin = new Skin(Gdx.files.internal("core/src/main/java/com/barrypress/dd/core/hud/assets/uiskin.json"));

        batch = new SpriteBatch();
        camera = new PerspectiveCamera();
        spriteSheet = assetManager.get("core/src/main/java/com/barrypress/dd/core/hud/assets/spritesheet.txt");

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        stage = new Stage(new StretchViewport((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight()), batch);
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.setSkin(skin);
        mainTable.left().top();

        Table leftSide = new Table();
        Table rightSide = new Table();
        Table portraits = new Table();
        Table character = new Table();
        Table characterDetails = new Table();
        Table characterSkills = new Table();

        leftSide.setSkin(skin);
        rightSide.setSkin(skin);
        portraits.setSkin(skin);
        character.setSkin(skin);
        characterDetails.setSkin(skin);
        characterSkills.setSkin(skin);

        Table characterInfo = new Table();
        characterInfo.setSkin(skin);
        characterInfo.setBackground(new SpriteDrawable(new Sprite(spriteSheet.findRegion("thor"))));
        characterInfo.add("AC");
        characterInfo.add("HP");
        characterInfo.add("Speed");
        characterInfo.add("Surge");

        characterDetails.add(characterInfo).colspan(2).height(250f);
        characterDetails.row();
        characterDetails.add("State1").expandY();
        characterDetails.add("State2");

        characterSkills.add("At Will");
        characterSkills.add("AW1");
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

        character.add(characterDetails);
        character.add(characterSkills);
        character.add("details");

        portraits.left().top();
        boolean flag = true;
        for (PC pc : characters) {
            pc.setPortrait(new Image(spriteSheet.findRegion(pc.getTag())));
            pc.getPortrait().setName(pc.getName());
            pc.getPortrait().addListener(new HoverListener(pc.getPortrait()));
            if (flag) {
                portraits.add(pc.getPortrait());
                portraits.add(new Image(spriteSheet.findRegion("blank_circle"))).expandX().top().right();
                flag = false;
            } else {
                portraits.add(pc.getPortrait()).colspan(2).top().left();
            }
            portraits.row();
        }

        leftSide.add(portraits).width(width * .8f).height(height * .7f);
        leftSide.row();
        leftSide.add(character).expandY().top().left();

        rightSide.add("mdetail");
        rightSide.row();
        rightSide.add("state");
        rightSide.row();
        rightSide.add("surge");
        rightSide.row();
        rightSide.add("phase");
        rightSide.row();
        rightSide.add("toolbar");

        mainTable.add(leftSide);
        mainTable.add(rightSide).expandX();

        stage.addActor(mainTable);
    }

    public void render() {

        Gdx.gl20.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.begin();
        bitmapFont.draw(this.batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 20.0F, 20.0F);
        batch.end();
        stage.setDebugAll(true);
        stage.act();
        stage.draw();
    }

}
