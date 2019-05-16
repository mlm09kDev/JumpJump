package com.mlm09kdev.jump_jump.Framework.Impl;


import android.view.MotionEvent;
import android.view.View;

import com.mlm09kdev.jump_jump.Framework.Input;
import com.mlm09kdev.jump_jump.Framework.Pool;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class SingleTouchHandler implements TouchHandler {
    private boolean isTouched;
    private int touchX;
    private int touchY;
    private final Pool<Input.TouchEvent> touchEventPool;
    private final List<Input.TouchEvent> touchEvents = new ArrayList<>();
    private final List<Input.TouchEvent> touchEventsBuffer = new ArrayList<>();
    private final float scaleX;
    private final float scaleY;

    public SingleTouchHandler(View view, float scaleX, float scaleY) {
        Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>() {
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }            
        };
        touchEventPool = new Pool<>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public boolean onTouch(View v, MotionEvent event) {
        synchronized(this) {
            Input.TouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
                isTouched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                touchEvent.type = Input.TouchEvent.TOUCH_DRAGGED;
                isTouched = true;
                break;
            case MotionEvent.ACTION_CANCEL:                
            case MotionEvent.ACTION_UP:
                touchEvent.type = Input.TouchEvent.TOUCH_UP;
                isTouched = false;
                break;
            }
            
            touchEvent.x = touchX = (int)(event.getX() * scaleX);
            touchEvent.y = touchY = (int)(event.getY() * scaleY);
            touchEventsBuffer.add(touchEvent);                        
            
            return true;
        }
    }

    public boolean isTouchDown(int pointer) {
        synchronized(this) {
            if(pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    public int getTouchX(int pointer) {
        synchronized(this) {
            return touchX;
        }
    }

    public int getTouchY(int pointer) {
        synchronized(this) {
            return touchY;
        }
    }

    public List<Input.TouchEvent> getTouchEvents() {
        synchronized(this) {     
            int len = touchEvents.size();
            for( int i = 0; i < len; i++ )
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}
