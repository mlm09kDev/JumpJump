package com.mlm09kdev.jump_jump;

import com.mlm09kdev.jump_jump.Framework.GL.Camera2D;
import com.mlm09kdev.jump_jump.Framework.GL.SpriteBatcher;
import com.mlm09kdev.jump_jump.Framework.Game;
import com.mlm09kdev.jump_jump.Framework.Impl.GLScreen;
import com.mlm09kdev.jump_jump.Framework.Input.*;
import com.mlm09kdev.jump_jump.Framework.Math.OverlapTester;
import com.mlm09kdev.jump_jump.Framework.Math.Rectangle;
import com.mlm09kdev.jump_jump.Framework.Math.Vector2;
import com.mlm09kdev.jump_jump.GameObjects.World;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class GameScreen extends GLScreen {
    public static final int GAME_READY = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 2;
    private static final int GAME_LEVEL_END = 3;
    public static final int GAME_OVER = 4;
    public static final int GAME_NOT_RUNNING = 5;

    public static int state;
    private final Camera2D guiCam;
    private final Vector2 touchPoint;
    private final SpriteBatcher batcher;
    private World world;
    private final World.WorldListener worldListener;
    private WorldRenderer renderer;
    private final Rectangle pauseBounds;
    private final Rectangle resumeBounds;
    private final Rectangle quitBounds;
    private int lastScore;
    private String scoreString;
    private boolean isAdReadytoLoad = false;

    public GameScreen(Game game) {
        super(game);
        state = GAME_READY;
        guiCam = new Camera2D(glGraphics, 320, 480);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        worldListener = new World.WorldListener() {
            public void jump() {
                Assets.playSound(Assets.jumpSound);
            }

            public void highJump() {
                Assets.playSound(Assets.highJumpSound);
            }

            public void hit() {
                Assets.playSound(Assets.hitSound);
            }

            public void coin() {
                Assets.playSound(Assets.coinSound);
            }
        };
        world = new World(worldListener);
        renderer = new WorldRenderer(glGraphics, batcher, world);
        pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
        resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
        quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
        if (World.level > 1)
            lastScore = World.score;
        else
            lastScore = 0;
        scoreString = "score: " + World.score;
    }

    @Override
    public void update(float deltaTime) {
        if (deltaTime > 0.1f)
            deltaTime = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateReady() {
        if (game.getInput().getTouchEvents().size() > 0) {
            state = GAME_RUNNING;
            isAdReadytoLoad = true;
        }
    }

    private void updateRunning(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type != TouchEvent.TOUCH_UP)
                continue;

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
                Assets.playSound(Assets.clickSound);
                state = GAME_PAUSED;
                return;
            }
        }

        world.update(deltaTime, game.getInput().getAccelX());
        if (World.score != lastScore || world.levelScore + World.score != lastScore) {
            lastScore = world.levelScore + World.score;
            scoreString = "" + lastScore;
        }
        if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
            state = GAME_LEVEL_END;
        }
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            if (World.score >= Settings.highscores[4])
                scoreString = "new highscore: " + World.score;
            else
                scoreString = "score: " + World.score;
            Settings.addScore(World.score);
            Settings.save(game.getFileIO());
        }
    }

    private void updatePaused() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type != TouchEvent.TOUCH_UP)
                continue;

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
                Assets.playSound(Assets.clickSound);
                state = GAME_RUNNING;
                return;
            }

            if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
                Assets.playSound(Assets.clickSound);
                state = GAME_NOT_RUNNING;
                game.setScreen(new MainMenuScreen(game));
                return;

            }
        }
    }

    private void updateLevelEnd() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type != TouchEvent.TOUCH_UP)
                continue;
            world = new World(worldListener);
            renderer = new WorldRenderer(glGraphics, batcher, world);
            World.score = lastScore;
            state = GAME_READY;
            Settings.save(game.getFileIO());

        }

    }

    private void updateGameOver() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type != TouchEvent.TOUCH_UP)
                continue;
            state = GAME_NOT_RUNNING;
            game.setScreen(new MainMenuScreen(game));
        }
        if (isAdReadytoLoad) {
            glGame.displayInterstitial();
            isAdReadytoLoad = false;
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        renderer.render();

        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_LEVEL_END:
                presentLevelEnd();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void presentReady() {
        String topText = "Level " + World.level;
        float topWidth = Assets.font.glyphWidth * topText.length();
        Assets.font.drawText(batcher, topText, 160 - topWidth / 2, 400);

        batcher.drawSprite(160, 240, 192, 32, Assets.ready);
    }

    private void presentRunning() {
        batcher.drawSprite(320 - 32, 480 - 60, 64, 64, Assets.pause);
        Assets.font.drawText(batcher, scoreString, 16, 480 - 60);
    }

    private void presentPaused() {
        batcher.drawSprite(160, 220, 192, 96, Assets.pauseMenu);
        Assets.font.drawText(batcher, scoreString, 16, 480 - 60);
    }

    private void presentLevelEnd() {
        String topText = 10 - world.getCoins() + "/10 Coins";
        float topWidth = Assets.font.glyphWidth * topText.length();
        Assets.font.drawText(batcher, topText, 180 - topWidth / 2, 480 - 80);

    }

    private void presentGameOver() {
        batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);
        float scoreWidth = Assets.font.glyphWidth * scoreString.length();
        Assets.font.drawText(batcher, scoreString, 160 - scoreWidth / 2, 480 - 60);
    }

    @Override
    public void pause() {
        if (state == GAME_RUNNING)
            state = GAME_PAUSED;
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
