package com.mlm09kdev.jump_jump.Framework.Impl;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class GLGraphics {
    private final GLSurfaceView glView;
    private GL10 gl;
    
    GLGraphics(GLSurfaceView glView) {
        this.glView = glView;
    }
    
    public GL10 getGL() {
        return gl;
    }
    
    void setGL(GL10 gl) {
        this.gl = gl;
    }
    
    public int getWidth() {
        return glView.getWidth();
    }
    
    public int getHeight() {
        return glView.getHeight();
    }
}
