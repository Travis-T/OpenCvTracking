package me.travis.camera;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


/**
 * Camera
 * Created by Travis on 11/20/2017.
 */
public class Main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("STARTING CAMERA");
       new Camera(0).startCamera();
    }

}
