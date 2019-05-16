package com.mlm09kdev.jump_jump.Framework;

import com.mlm09kdev.jump_jump.Framework.Graphics.PixmapFormat;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface Pixmap {
     int getWidth();

     int getHeight();

     PixmapFormat getFormat();

     void dispose();
}
