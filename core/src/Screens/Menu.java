package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class Menu extends ScreenAdapter {
	VideoPlayer videoPlayer = VideoPlayerCreator.createVideoPlayer();
	FileHandle backgroundVid =  Gdx.files.internal("Video/bg1.webm");
	ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal("Shader/default.vert"), Gdx.files.internal("Shader/color_tint_shader.frag"));
	SpriteBatch batch;
	Texture logo;
	Texture background;
	Music backgroundMusic;
	Button btnPlay;
	Button btnSettings;
	Button btnShop;
	Button btnExit;
	Stage stage;
	Game game;
	ShapeRenderer shapeRenderer;
	String username;
	SpriteBatch backgroundBatch;
	public Menu(Game game, String username) {
		this.game = game;
		this.username=username;
		backgroundBatch=new SpriteBatch();
	}
	@Override
	public void show(){
		float oW = 210;
		float oH = 90;
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();


		background = new Texture("Img/background.png");
		batch = new SpriteBatch();
		logo = new Texture("Img/logo.png");

		/* ----------------------------- BUTTONS -----------------------------------------*/
		btnPlay = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Play0.png"))));
		btnPlay.setSize(oW,oH);
		TextureRegionDrawable btnPlayN = new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Play0.png")));
		TextureRegionDrawable btnPlayH = new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Play1.png")));
		btnPlay.getStyle().up=btnPlayN;
		btnPlay.getStyle().down=btnPlayH;

		btnSettings = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Settings0.png"))));
		btnSettings.setSize(oW,oH);
		TextureRegionDrawable btnSettingsN = new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Settings0.png")));
		TextureRegionDrawable btnSettingsH = new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Settings1.png")));
		btnSettings.getStyle().up=btnSettingsN;
		btnSettings.getStyle().down=btnSettingsH;

		btnShop = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Shop0.png"))));
		btnShop.setSize(oW,oH);
		TextureRegionDrawable btnShopN = new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Shop0.png")));
		TextureRegionDrawable btnShopH = new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Shop1.png")));
		btnShop.getStyle().up=btnShopN;
		btnShop.getStyle().down=btnShopH;

		//COORDINATES
		btnPlay.setPosition(screenWidth/2 - oW/2, screenHeight-400);
		btnShop.setPosition(screenWidth/2 - oW/2, screenHeight-510);
		btnSettings.setPosition(screenWidth/2 - oW/2, screenHeight-630);

		//ClickListeners
		btnPlay.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
				// Change button texture on mouse hover
				btnPlay.getStyle().up = btnPlayH;
				btnPlay.addAction(Actions.sizeTo(oW * 1.2f, oH * 1.2f, 0.2f, Interpolation.smooth));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
				// Revert button texture when mouse exits
				btnPlay.getStyle().up = btnPlayN;
				btnPlay.addAction(Actions.sizeTo(oW, oH, 0.2f, Interpolation.smooth));
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new TrackSelection(game,username));
				dispose();
			}
		});

		btnShop.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
				// Change button texture on mouse hover
				btnShop.getStyle().up = btnShopH;
				btnShop.addAction(Actions.sizeTo(oW * 1.2f, oH * 1.2f, 0.2f, Interpolation.smooth));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
				// Revert button texture when mouse exits
				btnShop.getStyle().up = btnShopN;
				btnShop.addAction(Actions.sizeTo(oW, oH, 0.2f, Interpolation.smooth));
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("Button Click", "Play button clicked!");
			}
		});

		btnSettings.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
				// Change button texture on mouse hover
				btnSettings.getStyle().up = btnSettingsH;
				btnSettings.addAction(Actions.sizeTo(oW * 1.2f, oH * 1.2f, 0.2f, Interpolation.smooth));
			}


			@Override
			public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
				// Revert button texture when mouse exits
				btnSettings.getStyle().up = btnSettingsN;
				btnSettings.addAction(Actions.sizeTo(oW, oH, 0.2f, Interpolation.smooth));
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		stage = new Stage();
		stage.addActor(btnPlay);
		stage.addActor(btnSettings);
		stage.addActor(btnShop);
		Gdx.input.setInputProcessor(stage);
		try {
			videoPlayer.play(backgroundVid);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		videoPlayer.setLooping(true);

		/* ------------------------------------- END OF BUTTON SECTION ---------------------------------------*/

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/[Blue Archive] Theme 14 - Step by Step  'Homescreen BGM' for Blue Archive.mp3"));
		backgroundMusic.setVolume(0.5f);
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(1, 0, 0, 1);

		videoPlayer.update();
		backgroundBatch.setShader(shaderProgram);
		if (shaderProgram != null && shaderProgram.isCompiled()) {
			shaderProgram.begin();
			shaderProgram.setUniformf("u_tint", Color.ORANGE);
		}

		backgroundBatch.begin();
		backgroundBatch.draw(videoPlayer.getTexture(),0,0);
		backgroundBatch.end();

		if (shaderProgram != null && shaderProgram.isCompiled()) {
			shaderProgram.end();
		}
		backgroundBatch.setShader(null);

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float backgroundWidth = background.getWidth();
		float backgroundHeight = background.getHeight();

		float scale = Math.max(screenWidth / backgroundWidth, screenHeight / backgroundHeight);
		float scaledWidth = backgroundWidth * scale;
		float scaledHeight = backgroundHeight * scale;

		float logoWidth = logo.getWidth() * 0.42f;
		float logoHeight = logo.getHeight() * 0.42f;
		float logoX = (screenWidth - logoWidth) / 2;
		float logoY = screenHeight-280;

		stage.act((Gdx.graphics.getDeltaTime()));

		batch.begin();
		batch.draw(logo, logoX, logoY, logoWidth, logoHeight);
		batch.end();
		stage.draw();
	}

	@Override
	public void hide() {
		backgroundMusic.stop();
	}

	@Override
	public void dispose() {
		// Dispose of resources properly
		backgroundMusic.dispose();
		batch.dispose();
		logo.dispose();
		background.dispose();
		stage.dispose();
		videoPlayer.dispose();
	}
}
