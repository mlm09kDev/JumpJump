package com.mlm09kdev.jump_jump.Framework.Impl;

import android.media.SoundPool;

import com.mlm09kdev.jump_jump.Framework.Sound;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }
}
