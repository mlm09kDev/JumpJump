package com.mlm09kdev.jump_jump.Framework.Impl;

import android.graphics.Bitmap;

import com.mlm09kdev.jump_jump.Framework.Graphics;
import com.mlm09kdev.jump_jump.Framework.Pixmap;


/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    Graphics.PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public Graphics.PixmapFormat getFormat() {
        return format;
    }

    public void dispose() {
        bitmap.recycle();
    }      
}
