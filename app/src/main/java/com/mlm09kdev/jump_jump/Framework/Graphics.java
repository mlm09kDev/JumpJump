package com.mlm09kdev.jump_jump.Framework;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface Graphics {
    enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    Pixmap newPixmap(String fileName, PixmapFormat format);

    void clear(int color);

    void drawPixel(int x, int y, int color);

    void drawLine(int x, int y, int x2, int y2, int color);

    void drawRect(int x, int y, int width, int height, int color);

    void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
                    int srcWidth, int srcHeight);

    void drawPixmap(Pixmap pixmap, int x, int y);

    int getWidth();

    int getHeight();
}
