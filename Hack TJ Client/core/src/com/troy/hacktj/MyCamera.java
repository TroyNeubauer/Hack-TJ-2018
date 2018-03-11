package com.troy.hacktj;

public interface MyCamera {
    /**
     * Opens a picture preview on the user's device, calling the callback with the data when the picture is taken.
     * @param callback The callback to call when the picture's ready
     */
    public void takePicture(PictureCallback callback);

    public interface PictureCallback{
        public void pictureTaken(byte[] bytes);
    }
}
