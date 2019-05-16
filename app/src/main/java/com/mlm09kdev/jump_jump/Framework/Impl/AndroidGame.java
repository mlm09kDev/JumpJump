package com.mlm09kdev.jump_jump.Framework.Impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdView;
import com.mlm09kdev.jump_jump.Framework.Audio;
import com.mlm09kdev.jump_jump.Framework.FileIO;
import com.mlm09kdev.jump_jump.Framework.Game;
import com.mlm09kdev.jump_jump.Framework.Graphics;
import com.mlm09kdev.jump_jump.Framework.Input;
import com.mlm09kdev.jump_jump.Framework.Screen;


/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public abstract class AndroidGame extends Activity implements Game {
    private static final String AD_UNIT_ID = "test";
    private AndroidFastRenderView renderView;
    private Graphics graphics;
    private Audio audio;
    private Input input;
    private FileIO fileIO;
    private Screen screen;
    private WakeLock wakeLock;
    private AdView adView;
    private String TestDeviceID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame:myWakelockTag");


    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire(1000);
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (wakeLock.isHeld())
            wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {
        return screen;
    }
}
