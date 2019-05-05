package com.mlm09kdev.jump_jump.Framework.Impl;


import android.view.View.OnTouchListener;

import com.mlm09kdev.jump_jump.Framework.Input;

import java.util.List;


/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    
    public int getTouchX(int pointer);
    
    public int getTouchY(int pointer);
    
    public List<Input.TouchEvent> getTouchEvents();
}
