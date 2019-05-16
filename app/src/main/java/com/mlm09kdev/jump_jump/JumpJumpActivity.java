package com.mlm09kdev.jump_jump;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.mlm09kdev.jump_jump.Framework.Impl.GLGame;
import com.mlm09kdev.jump_jump.Framework.Screen;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class JumpJumpActivity extends GLGame {
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private final String[] appPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
    private Boolean firstTimeCreated = true;

    @Override
    public Screen getStartScreen() {
        if (checkAndRequestPermissions())
        {
        //Do something here if they accept
        }
            return new MainMenuScreen(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);

        if (firstTimeCreated) {
            Settings.load(getFileIO());
            Assets.load(this);
            firstTimeCreated = false;
        } else
            Assets.reload();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        Log.i("screen size", "Width + height " + width + height);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Settings.soundEnabled) {
            Assets.music.pause();
        }

    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : appPermission) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray((new String[listPermissionsNeeded.size()]))
                    ,PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage, save functionality disabled", Toast.LENGTH_SHORT).show();
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
