package me.travis.camera;

import me.travis.camera.utils.Colors;
import me.travis.camera.utils.DrawUtils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class FindObj {

    public static Mat findLargestGreenObj(Mat mat, RGBValue minRGB, RGBValue maxRGB) {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat uneditedFrame = mat;

        Mat hsvImage = new Mat();
        Mat eroded = new Mat();
        Mat dilated = new Mat();
        Mat ranged = new Mat();

        //converting to HSV so it is easier to read color
        Imgproc.cvtColor(uneditedFrame, hsvImage, Imgproc.COLOR_BGR2HSV);

        //getting color of green
        Core.inRange(hsvImage, new Scalar(minRGB.getR(), maxRGB.getG(), minRGB.getB()), new Scalar(maxRGB.getR(), maxRGB.getG(), maxRGB.getB()), ranged);


        Mat erode = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.erode(ranged, eroded, erode);
        Imgproc.erode(eroded, eroded, erode);

        Mat dilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.dilate(eroded, dilated, dilate);
        Imgproc.dilate(dilated, dilated, dilate);

        //finding countours of white pixels
        Imgproc.findContours(dilated, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        //going through all countours found and drawing a green rectangle aroud them on the unedited frame

        /**
         * Getting the largest contour and drawing
         * a rectangle around it
         */
        if (contours.size() > 0) {
            RotatedRect largestRect = null;
            for (MatOfPoint c : contours) {
                RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(c.toArray()));
                if (largestRect == null || largestRect.size.area() < rect.size.area()) {
                    largestRect = rect;
                }
            }

            DrawUtils.drawCircle(uneditedFrame, largestRect, Colors.RED.get(), 4);
            DrawUtils.drawRotatedRect(uneditedFrame, largestRect, new Scalar(0, 0, 0), 4);


        }
        return uneditedFrame;
    }
}