package me.travis.camera;

import org.opencv.core.*;

public class Main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("STARTING CAMERA");
       new Camera(0).startCamera();
    }

}
