package com.troy.hacktj.desktop;

import com.troy.hacktj.MyCamera;

public class DesktopCamera implements MyCamera {
    @Override
    public void takePicture(PictureCallback callback) {
        callback.pictureTaken(null);
    }
}
