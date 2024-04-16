package Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class TypeBeat extends Game {
	SpriteBatch batch;
	Texture logo;
	Texture background;
	Music backgroundMusic;
	Button btnPlay;
	Button btnSettings;
	Button btnShop;
	Button btnExit;
	Stage stage;



	@Override
	public void create() {
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
				Gdx.app.log("Button Click", "Play button clicked!");
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
				Gdx.app.log("Button Click", "Play button clicked!");
			}
		});

		stage = new Stage();
		stage.addActor(btnPlay);
		stage.addActor(btnSettings);
		stage.addActor(btnShop);
		Gdx.input.setInputProcessor(stage);

		/* ------------------------------------- END OF BUTTON SECTION ---------------------------------------*/

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/[Blue Archive] Theme 14 - Step by Step  'Homescreen BGM' for Blue Archive.mp3"));
		backgroundMusic.setVolume(0.5f);
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);

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
		batch.draw(background, 0, 0, scaledWidth, scaledHeight);
		batch.draw(logo, logoX, logoY, logoWidth, logoHeight);
		batch.end();
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		logo.dispose();
		background.dispose();
		backgroundMusic.dispose();
		stage.dispose();
	}
}
