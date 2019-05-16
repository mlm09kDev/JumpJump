package com.mlm09kdev.jump_jump.Framework.Impl;


import android.view.View.OnTouchListener;

import com.mlm09kdev.jump_jump.Framework.Input;

import java.util.List;


/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
interface TouchHandler extends OnTouchListener {
    boolean isTouchDown(int pointer);
    
    int getTouchX(int pointer);
    
    int getTouchY(int pointer);
    
    List<Input.TouchEvent> getTouchEvents();
}
