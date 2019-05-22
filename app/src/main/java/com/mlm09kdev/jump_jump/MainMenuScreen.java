package com.mlm09kdev.jump_jump;


import com.mlm09kdev.jump_jump.Framework.GL.Camera2D;
import com.mlm09kdev.jump_jump.Framework.GL.SpriteBatcher;
import com.mlm09kdev.jump_jump.Framework.Game;
import com.mlm09kdev.jump_jump.Framework.Impl.GLScreen;
import com.mlm09kdev.jump_jump.Framework.Input;
import com.mlm09kdev.jump_jump.Framework.Math.OverlapTester;
import com.mlm09kdev.jump_jump.Framework.Math.Rectangle;
import com.mlm09kdev.jump_jump.Framework.Math.Vector2;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;


public class MainMenuScreen extends GLScreen {

    private final int screenWidth = 1080;
    private final int screenHeight = 1794;

    int backgroundX = 540;
    int backgroundY = 897;


    private final int logoX = 540;
    private final int logoY = 1345;
    private final int logoWidth = 800;
    private final int logoHeight = 500;

    private final int mainMenuX = screenWidth/2;
    private final int mainMenuY = screenWidth-300;
    private final int mainMenuWidth = 800;
    private final int mainMenuHeight = 498;

    private final int soundIconXY = 64*2;
    private final int SoundIconWidthHeight = 128;


    private final Camera2D guiCam;
    private final SpriteBatcher batcher;
    private final Rectangle soundBounds;
    private final Rectangle playBounds;
    private final Rectangle highscoresBounds;
    private final Rectangle helpBounds;
    private final Vector2 touchPoint;
    private final Rectangle test;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, screenWidth, screenHeight);
        batcher = new SpriteBatcher(glGraphics, mainMenuHeight);

/*        playBounds = new Rectangle(160-150, 200+18, 300, 36);
        highscoresBounds = new Rectangle(160-150, 200-18, 300, 36);
        helpBounds = new Rectangle(160-150, 200-18-36, 300, 36);*/

        playBounds = new Rectangle(mainMenuX, mainMenuY+165, mainMenuWidth-400, mainMenuHeight-315);
        highscoresBounds = new Rectangle(mainMenuX, mainMenuY-10, mainMenuWidth+50, mainMenuHeight-315);
        helpBounds = new Rectangle(mainMenuX, mainMenuY-185, mainMenuWidth-350, mainMenuHeight-315);
        soundBounds = new Rectangle(0, 0, SoundIconWidthHeight, SoundIconWidthHeight);
        touchPoint = new Vector2();
        test = new Rectangle(screenWidth, screenHeight, 50, 50);

    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                if (OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    game.setScreen(new GameScreen(game));
                    return;
                }
                if (OverlapTester.pointInRectangle(highscoresBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    game.setScreen(new HighScoreScreen(game));
                    return;
                }
             /*   if (OverlapTester.pointInRectangle(helpBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    game.setScreen(new HelpScreen(game));
                    return;
                }*/
                if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if (Settings.soundEnabled)
                        Assets.music.play();
                    else
                        Assets.music.pause();
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

        batcher.beginBatch(Assets.background);
        batcher.drawSprite(backgroundX, backgroundY, screenWidth, screenHeight, Assets.backgroundRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);

        batcher.drawSprite(logoX, logoY, logoWidth, logoHeight, Assets.logo);
        batcher.drawSprite(mainMenuX, mainMenuY, mainMenuWidth, mainMenuHeight, Assets.mainMenu);
       // batcher.drawSprite(mainMenuX, mainMenuY+165, mainMenuWidth-400, mainMenuHeight-315, Assets.testRec);

        batcher.drawSprite(soundIconXY, soundIconXY, SoundIconWidthHeight * 2, SoundIconWidthHeight * 2, Settings.soundEnabled ? Assets.soundOn : Assets.soundOff);

        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
