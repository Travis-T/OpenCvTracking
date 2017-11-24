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
    private Runnable cameraTask;
    private CameraFrame panel;
    private ScheduledExecutorService executor;
    private int framesInSecond;
    private int fps;
    private int cameraDeadCalls;

    private RGBValue minRGB;
    private RGBValue maxRGB;


    /**
     *
     * @param cameraIndex  the index of the camera
     */
    public Camera(int cameraIndex) {
        this.cameraIndex = cameraIndex;
        this.capture = new VideoCapture();
        this.capture.open(cameraIndex);

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

    /**
     * Launches the camera Task and the Fps task if the camera is active
     */
    public void startCamera() {
        if(capture.isOpened()) {
            executor = Executors.newScheduledThreadPool(2);

            /**
             * Reads a frame, stores it inaZaz
             */
            cameraTask = () -> {
                capture.read(currentFrame);
                displayImage();
            };

            Runnable fpsTask = () -> {
              fps = framesInSecond;
              if(framesInSecond == 0) {
                  if(cameraDeadCalls == 2) {
                      executor.shutdown();
                  }
                  cameraDeadCalls++;
              }
              framesInSecond = 0;
            };

            //8 FPS, 4 if there are a lot of objects.
            executor.scheduleAtFixedRate(cameraTask, 0, 100, TimeUnit.MILLISECONDS);
            executor.scheduleWithFixedDelay(fpsTask, 1, 1, TimeUnit.SECONDS);

        } else {
            System.out.println("ERROR: Camera is not open!");
        }
    }


    private void displayImage() {
        if(currentFrame == null)
            return;
        Mat frame = currentFrame;
        FindObj.findLargestGreenObj(frame, minRGB, maxRGB);

        DrawUtils.drawText(frame, "FPS: " + fps, DrawUtils.Location.TOP_LEFT, Colors.GREEN.get());
        if(panel.repaintImage(new ImageIcon(Mat2BufferedImage(frame))))
            framesInSecond++;


    }


    public BufferedImage Mat2BufferedImage(Mat m){
        //source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
        //Fastest code
        //The output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}