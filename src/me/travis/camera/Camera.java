package me.travis.camera;

import me.travis.camera.utils.Colors;
import me.travis.camera.utils.DrawUtils;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Camera {

    private VideoCapture capture;
    private int cameraIndex;
    private Mat currentFrame;
    private CameraFrame panel;
    private ScheduledExecutorService executor;
    private int framesInSecond;
    private int fps;

    private RGBValue minRGB;
    private RGBValue maxRGB;

    /**
     * @param cameraIndex the index of the camera
     */
    public Camera(int cameraIndex) {
        this.cameraIndex = cameraIndex;
        this.capture = new VideoCapture();
        this.currentFrame = new Mat();
        this.panel = new CameraFrame(new JLabel(new ImageIcon("E:\\Travis\\default.jpg")), this);
        fps = 0;
        minRGB = new RGBValue(44, 46, 57);
        maxRGB = new RGBValue(89, 255, 255);
    }

    public RGBValue getMaxRGB() {
        return maxRGB;
    }

    public void setMaxRGB(RGBValue maxRGB) {
        this.maxRGB = maxRGB;
    }

    public RGBValue getMinRGB() {
        return minRGB;
    }

    public void setMinRGB(RGBValue minRGB) {
        this.minRGB = minRGB;
    }

    public void setCurrentFrame(Mat currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Launches the camera Task and the Fps task if the camera is active
     */
    public void startCamera() {
        if (!capture.isOpened()) {
            try {
                capture.open(cameraIndex);
            } catch (Exception e) {
                System.out.println("ERROR: Could not find camera at index " + cameraIndex + "!");
                return;
            }
        }
        executor = Executors.newScheduledThreadPool(3);

        /**
         * Reads a frame, stores it in currentFrame (Matrix)
         */
        //low FPS NOT webcam, opencv problem. Multithreading?
        Runnable displayTask = () -> {
            capture.read(currentFrame);
            framesInSecond++;
            displayImage();
        };

        Runnable fpsTask = () -> {
            fps = framesInSecond;
            framesInSecond = 0;
        };

        //145ms because that is the avg ms it takes to process, 90% being capture#read()
        executor.scheduleAtFixedRate(displayTask, 30, 65, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(fpsTask, 1, 1, TimeUnit.SECONDS);

    }


    private void displayImage() {
        if (currentFrame.empty()) {
            System.out.println("ERROR: FRAME EMPTY SHUTTING DOWN!");
            System.exit(0);
            return;
        }
        Mat frame = currentFrame;
        //FindObj.findLargestGreenObj(frame, minRGB, maxRGB);


        DrawUtils.drawText(frame, "FPS: " + fps, DrawUtils.Location.TOP_LEFT, Colors.GREEN.get());
        panel.repaintImage(new ImageIcon(Mat2BufferedImage(frame)));
    }

    /**
     * Takes a matrix and turns it into an image you can display
     *
     * @param m     The matrix
     * @return      The image file
     */
    public BufferedImage Mat2BufferedImage(Mat m) {
        //source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
        //Fastest code
        //The output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}