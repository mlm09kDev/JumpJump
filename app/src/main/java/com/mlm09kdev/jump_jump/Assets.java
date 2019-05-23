package com.mlm09kdev.jump_jump;

import com.mlm09kdev.jump_jump.Framework.GL.Animation;
import com.mlm09kdev.jump_jump.Framework.GL.Font;
import com.mlm09kdev.jump_jump.Framework.GL.Texture;
import com.mlm09kdev.jump_jump.Framework.GL.TextureRegion;
import com.mlm09kdev.jump_jump.Framework.Impl.GLGame;
import com.mlm09kdev.jump_jump.Framework.Music;
import com.mlm09kdev.jump_jump.Framework.Sound;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
class Assets {
    //Todo: update end level graphics
    //Todo: Make High Score persistent.
    //Todo: Update High Score Page to be more useful.
    //Todo: Fix Multi-touch crashing
    //Todo: Fix BackButton Bug of not saving Game.
    //Todo: Fix Bounding Box for Touch Events.
    //Todo: Add Restart Option on Pause Menu.
    //Todo: Remove Platform pulverize options.
    //Todo: Balance Levels and point Gathering.
    //

    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture items;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScoresRegion;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrow;
    public static TextureRegion pause;
    public static TextureRegion spring;
    public static TextureRegion castle;
    public static Animation coinAnim;
    public static Animation playerJump;
    public static Animation playerFall;
    public static TextureRegion playerHit;
    public static Animation squirrelFly;
    public static TextureRegion platform;
    public static Animation brakingPlatform;
    public static Animation sprigAnimation;
    public static Font font;


    public static Music music;
    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound hitSound;
    public static Sound coinSound;
    public static Sound clickSound;

    public static void load(GLGame game) {
        background = new Texture(game, "background-2-2.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 1080, 1920);


        items = new Texture(game, "items-test.png");
        mainMenu = new TextureRegion(items, 0, 224 * 2, 300 * 2, 110 * 2);
        pauseMenu = new TextureRegion(items, 224 * 2, 128 * 2, 192 * 2, 96 * 2);
        ready = new TextureRegion(items, 320 * 2, 224 * 2, 192 * 2, 32 * 2);
        gameOver = new TextureRegion(items, 352 * 2, 256 * 2, 160 * 2, 96 * 2);
        highScoresRegion = new TextureRegion(items, 0, 257 * 2, 300 * 2, (110 * 2) / 3);
        logo = new TextureRegion(items, 0, 352 * 2, 274 * 2, 142 * 2);
        soundOff = new TextureRegion(items, 0, 0, 64 * 2, 64 * 2);
        soundOn = new TextureRegion(items, 64 * 2, 0, 64 * 2, 64 * 2);
        arrow = new TextureRegion(items, 0, 64 * 2, 64 * 2, 64 * 2);
        pause = new TextureRegion(items, 64 * 2, 64 * 2, 64 * 2, 64 * 2);

        spring = new TextureRegion(items, 0, 380, 100, 27);

        sprigAnimation = new Animation(.02f,
                new TextureRegion(items, 100, 380, 100, 27),
                new TextureRegion(items, 200, 380, 100, 27),
                new TextureRegion(items, 100, 380, 100, 27),
                new TextureRegion(items, 0, 380, 100, 27));

        castle = new TextureRegion(items, 128 * 2, 64 * 2, 64 * 2, 64 * 2);
        coinAnim = new Animation(.2f,
                new TextureRegion(items, 280, 0, 64, 64),
                new TextureRegion(items, 360, 0, 64, 64),
                new TextureRegion(items, 280, 70, 64, 64),
                new TextureRegion(items, 360, 70, 64, 64),
                new TextureRegion(items, 280, 70, 64, 64),
                new TextureRegion(items, 360, 0, 64, 64));
        playerJump = new Animation(0.2f,
                new TextureRegion(items, 0, 128 * 2, 32 * 2, 32 * 2),
                new TextureRegion(items, 32 * 2, 128 * 2, 32 * 2, 32 * 2));
        playerFall = new Animation(0.2f,
                new TextureRegion(items, 64 * 2, 128 * 2, 32 * 2, 32 * 2),
                new TextureRegion(items, 96 * 2, 128 * 2, 32 * 2, 32 * 2));
        playerHit = new TextureRegion(items, 128*2, 128*2, 32*2, 32*2);
        squirrelFly = new Animation(0.2f,
                new TextureRegion(items, 0, 160 * 2, 32 * 2, 32 * 2),
                new TextureRegion(items, 32 * 2, 160 * 2, 32 * 2, 32 * 2));
        platform = new TextureRegion(items, 64 * 2, 160 * 2, 64 * 2, 51);
        brakingPlatform = new Animation(0.2f,
                new TextureRegion(items, 64 * 2, 160 * 2, 64 * 2, 16 * 2),
                new TextureRegion(items, 64 * 2, 176 * 2, 64 * 2, 16 * 2),
                new TextureRegion(items, 64 * 2, 192 * 2, 64 * 2, 16 * 2),
                new TextureRegion(items, 64 * 2, 208 * 2, 64 * 2, 16 * 2));


        font = new Font(items, 225 * 2, 0, 16, 32, 40);

        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if (Settings.soundEnabled)
            music.play();
        jumpSound = game.getAudio().newSound("jump.ogg");
        highJumpSound = game.getAudio().newSound("highjump.ogg");
        hitSound = game.getAudio().newSound("hit.ogg");
        coinSound = game.getAudio().newSound("coin.ogg");
        clickSound = game.getAudio().newSound("click.ogg");
    }

    public static void reload() {
        background.reload();
        items.reload();
        if (Settings.soundEnabled)
            music.play();
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled)
            sound.play(1);
    }

}
