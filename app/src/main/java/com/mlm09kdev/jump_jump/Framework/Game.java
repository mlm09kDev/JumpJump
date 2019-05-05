package com.mlm09kdev.jump_jump.Framework;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface Game {
    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}