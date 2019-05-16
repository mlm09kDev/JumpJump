package com.mlm09kdev.jump_jump.Framework.Impl;


import com.mlm09kdev.jump_jump.Framework.Game;
import com.mlm09kdev.jump_jump.Framework.Screen;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public abstract class GLScreen extends Screen {
    protected final GLGraphics glGraphics;
    protected final GLGame glGame;
    
    protected GLScreen(Game game) {
        super(game);
        glGame = (GLGame)game;
        glGraphics = glGame.getGLGraphics();
    }
}
