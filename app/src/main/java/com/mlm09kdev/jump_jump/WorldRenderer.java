package com.mlm09kdev.jump_jump;

import com.mlm09kdev.jump_jump.Framework.GL.Animation;
import com.mlm09kdev.jump_jump.Framework.GL.Camera2D;
import com.mlm09kdev.jump_jump.Framework.GL.SpriteBatcher;
import com.mlm09kdev.jump_jump.Framework.GL.TextureRegion;
import com.mlm09kdev.jump_jump.Framework.Impl.GLGraphics;
import com.mlm09kdev.jump_jump.GameObjects.Castle;
import com.mlm09kdev.jump_jump.GameObjects.Coin;
import com.mlm09kdev.jump_jump.GameObjects.Platform;
import com.mlm09kdev.jump_jump.GameObjects.Player;
import com.mlm09kdev.jump_jump.GameObjects.Spring;
import com.mlm09kdev.jump_jump.GameObjects.Squirrel;
import com.mlm09kdev.jump_jump.GameObjects.World;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
class WorldRenderer {
    private static final float FRUSTUM_WIDTH = 10;
    private static final float FRUSTUM_HEIGHT = 15;
    private final GLGraphics glGraphics;
    private final World world;
    private final Camera2D cam;
    private final SpriteBatcher batcher;

    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;
    }

    public void render() {
        if(world.player.position.y > cam.position.y )
            cam.position.y = world.player.position.y;
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
    }

    private void renderBackground() {
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(cam.position.x, cam.position.y,
                FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
                Assets.backgroundRegion);
        batcher.endBatch();
    }

    private void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);
        renderPlayer();
        renderPlatforms();
        renderItems();
        renderSquirrels();
        renderCastle();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void renderPlayer() {
        TextureRegion keyFrame;
        switch(world.player.state) {
            case Player.PLAYER_STATE_FALL:
                keyFrame = Assets.playerFall.getKeyFrame(world.player.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case Player.PLAYER_STATE_JUMP:
                keyFrame = Assets.playerJump.getKeyFrame(world.player.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case Player.PLAYER_STATE_HIT:
            default:
                keyFrame = Assets.playerHit;
        }

        float side = world.player.velocity.x < 0? -1: 1;
        batcher.drawSprite(world.player.position.x, world.player.position.y, side * 1, 1, keyFrame);
    }

    private void renderPlatforms() {
        int len = world.platforms.size();
        for(int i = 0; i < len; i++) {
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Assets.platform;
            if(platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
                keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
            }

            batcher.drawSprite(platform.position.x, platform.position.y,
                    2, 0.5f, keyFrame);
        }
    }

    private void renderItems() {
        int len = world.springs.size();
        for(int i = 0; i < len; i++) {
            Spring spring = world.springs.get(i);
            batcher.drawSprite(spring.position.x, spring.position.y, 1, 1, Assets.spring);
        }

        len = world.coins.size();
        for(int i = 0; i < len; i++) {
            Coin coin = world.coins.get(i);
            TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
            batcher.drawSprite(coin.position.x, coin.position.y, 1, 1, keyFrame);
        }
    }

    private void renderSquirrels() {
        int len = world.squirrels.size();
        for(int i = 0; i < len; i++) {
            Squirrel squirrel = world.squirrels.get(i);
            TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
            float side = squirrel.velocity.x < 0?-1:1;
            batcher.drawSprite(squirrel.position.x, squirrel.position.y, side * 1, 1, keyFrame);
        }
    }

    private void renderCastle() {
        Castle castle = world.castle;
        batcher.drawSprite(castle.position.x, castle.position.y, 2, 2, Assets.castle);
    }
}
