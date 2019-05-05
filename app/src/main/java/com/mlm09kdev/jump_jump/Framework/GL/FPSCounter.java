package com.mlm09kdev.jump_jump.Framework.GL;

import android.util.Log;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class FPSCounter {
    long startTime = System.nanoTime();
    int frames = 0;
    
    public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
} 
