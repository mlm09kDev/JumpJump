package com.mlm09kdev.jump_jump;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.mlm09kdev.jump_jump.Framework.Game;
import com.mlm09kdev.jump_jump.Framework.Impl.GLGame;
import com.mlm09kdev.jump_jump.Framework.Screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class JumpJumpActivity extends GLGame {
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private final String[] appPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Boolean firstTimeCreated = true;

    @Override
    public Screen getStartScreen() {
        if (checkAndRequestPermissions()) {
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
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()])
                    , PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0) {
                // run app
            } else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        showDialog("", "This app need Storage permissions to work without any problems.",
                                "Yes, Grant permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkAndRequestPermissions();
                                    }
                                }, "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }, false);
                    } else {
                        showDialog("", "You have denied some permissions. Allow all Permnissions at [Setting] > [Permissions]",
                                "Go to Settings",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }, "no, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }

                                }, false);
                        break;
                    }
                }
            }
        }
    }

    public AlertDialog showDialog(String tittle, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLabel, positiveOnClick);
        builder.setNegativeButton(negativeLabel, negativeOnClick);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    @Override
    public void onBackPressed() {
        if(  GameScreen.state == GameScreen.GAME_RUNNING)
        GameScreen.state = GameScreen.GAME_PAUSED;

        if(GameScreen.state == GameScreen.GAME_NOT_RUNNING || GameScreen.state == GameScreen.GAME_READY)
            finish();
    }
}
