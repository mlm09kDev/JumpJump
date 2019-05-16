package com.mlm09kdev.jump_jump.Framework;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface Music {
     void play();

     void stop();

     void pause();

     void setLooping(boolean looping);

     void setVolume(float volume);

     boolean isPlaying();

     boolean isStopped();

     boolean isLooping();

     void dispose();
}
