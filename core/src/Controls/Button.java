package Controls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button extends Image {

    private Texture buttonTextureNormal;
    private Texture buttonTextureHover;
    private boolean isPressed;

    public Button(String normalTexturePath, String hoverTexturePath, float width, float height) {
        super(new TextureRegionDrawable(new TextureRegion(new Texture(normalTexturePath))));
        this.buttonTextureNormal = new Texture(normalTexturePath);
        this.buttonTextureHover = new Texture(hoverTexturePath);
        this.isPressed = false;

        // Set button size
        this.setSize(width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha); // Call super method to draw the image
    }

    @Override
    public void act(float delta) {
        super.act(delta); // Call super method to perform base actor behavior
    }

    // Custom method to handle button press event
    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }


}
