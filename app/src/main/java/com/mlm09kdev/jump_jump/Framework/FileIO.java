package com.mlm09kdev.jump_jump.Framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public interface FileIO {
     InputStream readAsset(String fileName) throws IOException;

     InputStream readFile(String fileName) throws IOException;

     OutputStream writeFile(String fileName) throws IOException;
}
