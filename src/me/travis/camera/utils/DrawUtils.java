package me.travis.camera.utils;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

/**
 * Camera
 * Created by Travis on 11/23/2017.
 */
public class DrawUtils {

    public static void drawRotatedRect(Mat image, RotatedRect rotatedRect, Scalar color, int thickness) {
        Point[] vertices = new Point[4];
        rotatedRect.points(vertices);
        MatOfPoint points = new MatOfPoint(vertices);

        Imgproc.drawContours(image, Arrays.asList(points), -1, color, thickness);
    }

    public static void drawCircle(Mat image, RotatedRect rotatedRect, Scalar color, int thickness) {
        double height = rotatedRect.size.height;
        double width = rotatedRect.size.width;
        int radius = (int) (height > width ? height : width) / 2;
        Imgproc.circle(image, rotatedRect.center, radius, color, thickness);
    }

    public static void drawText(Mat image, String text, Location location, Scalar color) {
        Imgproc.putText(image, text, location.get(image, text), 1, 1, color);
    }

    public enum Location {

        TOP_LEFT(0), TOP_MIDDLE(1), TOP_RIGHT(2),
        MIDDLE_LEFT(3), MIDDLE_MIDDLE(4), MIDDLE_RIGHT(5),
        BOTTOM_LEFT(6), BOTTOM_MIDDLE(7), BOTTOM_RIGHT(8);

        private int type;
        Location(int type) {
            this.type = type;
        }

        public Point get(Mat mat, String text) {
            int length = text.length();
            switch (type) {
                case 0:
                    return new Point(10, 10);
                case 1:
                    return new Point(mat.width() / 2, 10);
                case 2:
                    return new Point(mat.width() - length * 9.5, 10);
                case 3:
                    return new Point(10, mat.height() / 2);
                case 4:
                    return new Point(mat.width() / 2, mat.height() / 2);
                case 5:
                    return new Point(mat.width() - length * 9.5, mat.height() / 2);
                case 6:
                    return new Point(10, mat.height() - 10);
                case 7:
                    return new Point(mat.width() / 2, mat.height() - 10);
                case 8:
                    return new Point(mat.width() - length * 9.5, mat.height() - 10);
            }
            return null;
        }
    }

}