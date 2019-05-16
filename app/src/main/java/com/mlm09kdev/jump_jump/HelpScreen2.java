package com.mlm09kdev.jump_jump;

import com.mlm09kdev.jump_jump.Framework.GL.Camera2D;
import com.mlm09kdev.jump_jump.Framework.GL.SpriteBatcher;
import com.mlm09kdev.jump_jump.Framework.GL.Texture;
import com.mlm09kdev.jump_jump.Framework.GL.TextureRegion;
import com.mlm09kdev.jump_jump.Framework.Game;
import com.mlm09kdev.jump_jump.Framework.Impl.GLScreen;
import com.mlm09kdev.jump_jump.Framework.Input;
import com.mlm09kdev.jump_jump.Framework.Math.OverlapTester;
import com.mlm09kdev.jump_jump.Framework.Math.Rectangle;
import com.mlm09kdev.jump_jump.Framework.Math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class HelpScreen2 extends GLScreen {

    private final Camera2D guiCam;
    private final SpriteBatcher batcher;
    private final Rectangle nextBounds;
    private final Vector2 touchPoint;
    private Texture helpImage;
    private TextureRegion helpRegion;


    public HelpScreen2(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 320, 480);
        nextBounds = new Rectangle(320 - 64, 0, 64, 64);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(OverlapTester.pointInRectangle(nextBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    game.setScreen(new HelpScreen3(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(helpImage);
        batcher.drawSprite(160, 240, 320, 480, helpRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);
        batcher.drawSprite(320 - 32, 32, -64, 64, Assets.arrow);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {
        helpImage.dispose();
    }

    @Override
    public void resume() {
        helpImage = new Texture(glGame, "help2.png" );
        helpRegion = new TextureRegion(helpImage, 0, 0, 320, 480);
    }

    @Override
    public void dispose() {

    }
}
