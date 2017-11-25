package me.travis.camera.utils;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

public class DrawUtils {

    private DrawUtils() {}
    /**
     * Draws a rectangle outline on the image.
     * @param image         The image to draw the rectangle on
     * @param rotatedRect   The location to draw the rectangle
     * @param color         The color of the rectangle
     * @param thickness     The thickness of the outline
     */
    public static void drawRotatedRect(Mat image, RotatedRect rotatedRect, Scalar color, int thickness) {
        Point[] vertices = new Point[4];
        rotatedRect.points(vertices);
        MatOfPoint points = new MatOfPoint(vertices);

        Imgproc.drawContours(image, Arrays.asList(points), -1, color, thickness);
    }

    /**
     * Draws a circle outline on the image.
     * @param image         The image to draw the circle on
     * @param rotatedRect   The location to draw the circle
     * @param color         The color of the circle
     * @param thickness     The thickness of the outline
     */
    public static void drawCircle(Mat image, RotatedRect rotatedRect, Scalar color, int thickness) {
        double height = rotatedRect.size.height;
        double width = rotatedRect.size.width;
        int radius = (int) (height > width ? height : width) / 2;
        Imgproc.circle(image, rotatedRect.center, radius, color, thickness);
    }

    /**
     * Writes text on an image.
     * @param image         The image to write the text on
     * @param text          The text to write
     * @param location      The location to put the text
     * @param color         The color of the tect
     */
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
            Point point = null;
            switch (type) {
                case 0:
                    point =  new Point(10, 10);
                    break;
                case 1:
                    point = new Point(mat.width() / 2, 10);
                    break;
                case 2:
                    point = new Point(mat.width() - length * 9.5, 10);
                    break;
                case 3:
                    point = new Point(10, mat.height() / 2);
                    break;
                case 4:
                    point = new Point(mat.width() / 2, mat.height() / 2);
                    break;
                case 5:
                    point = new Point(mat.width() - length * 9.5, mat.height() / 2);
                    break;
                case 6:
                    point = new Point(10, mat.height() - 10);
                    break;
                case 7:
                    point = new Point(mat.width() / 2, mat.height() - 10);
                    break;
                case 8:
                    point = new Point(mat.width() - length * 9.5, mat.height() - 10);
                default: break;
            }
            return point;
        }
    }

}