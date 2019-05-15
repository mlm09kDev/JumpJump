package com.mlm09kdev.jump_jump;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mlm09kdev.jump_jump.Framework.Impl.GLGame;
import com.mlm09kdev.jump_jump.Framework.Screen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class JumpJumpActivity extends GLGame {
    Boolean firstTimeCreated = true;

    @Override
    public Screen getStartScreen() {
        return new MainMenuScreen(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        if (firstTimeCreated) {
            Settings.load(getFileIO());
            Assets.load(this);
            firstTimeCreated = false;
        } else
            Assets.reload();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        Log.i("screen size", "Width + height " + width + height);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Settings.soundEnabled) {
        Assets.music.pause();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
