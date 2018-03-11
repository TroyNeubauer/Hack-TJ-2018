package com.troy.hacktj;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class AndroidCamera implements MyCamera {
    private Activity activity;

    public AndroidCamera(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void takePicture(final PictureCallback callback) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Camera.PictureCallback myPictureCallback_jpeg = new Camera.PictureCallback() {
                    public void onPictureTaken(byte[] imageData, Camera c) {
                        callback.pictureTaken(imageData);
                    }
                };

                SurfaceView mySurfaceView = new SurfaceView(activity);
                SurfaceHolder mySurfaceHolder = mySurfaceView.getHolder();
                Camera  myCamera = Camera.open();
                if(myCamera == null) {
                    callback.pictureTaken(null);
                    return;
                }
                try {
                    myCamera.setPreviewDisplay(mySurfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Camera.Parameters p = myCamera.getParameters();
                p.setJpegQuality(90);//a value between 1 and 100
                myCamera.setParameters(p);
                myCamera.startPreview();
                myCamera.takePicture(null, null, null, myPictureCallback_jpeg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myCamera.release();
            }
        });

    }
}
