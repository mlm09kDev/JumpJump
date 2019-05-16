package com.mlm09kdev.jump_jump.Framework;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface Game {
     Input getInput();

     FileIO getFileIO();

     Graphics getGraphics();

     Audio getAudio();

     void setScreen(Screen screen);

     Screen getCurrentScreen();

     Screen getStartScreen();
}