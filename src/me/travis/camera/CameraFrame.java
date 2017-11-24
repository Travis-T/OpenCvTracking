package me.travis.camera;

import javax.swing.*;
import java.awt.*;

public class CameraFrame extends JFrame {

    private Camera camera;
    private JLabel label;
    private JSlider sliderMinR, sliderMinB, sliderMinG, sliderMaxR, sliderMaxB, sliderMaxG;
    public CameraFrame(JLabel label, Camera camera) {
        super("Camera");
        super.setSize(800, 800);
        setLayout(new FlowLayout());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.add(label);
        sliderMinR = slider(0, 255, 44, 20, true, true);
        sliderMinB = slider(0, 255, 46, 20, true, true);
        sliderMinG = slider(0, 255, 57, 20, true, true);
        sliderMaxR = slider(0, 255, 89, 20, true, true);
        sliderMaxB = slider(0, 255, 255, 20, true, true);
        sliderMaxG = slider(0, 255, 255, 20, true, true);
        super.add(sliderMinR);
        super.add(sliderMinB);
        super.add(sliderMinG);
        super.add(sliderMaxR);
        super.add(sliderMaxB);
        super.add(sliderMaxG);

        super.setVisible(true);
        this.label = label;
        this.camera = camera;
    }

    public boolean repaintImage(ImageIcon imageIcon) {
        if (label != null) {
            label.setIcon(imageIcon);
            label.repaint();
            updateRGB();
            return true;
        }
        return false;
    }

    public void updateRGB() {
        //System.out.println("Min R: " + sliderMinR.getValue() + "| Min B: " + sliderMinB.getValue() + "|  Min G: " + sliderMinG.getValue());
       // System.out.println("Max R: " + sliderMaxR.getValue() + "| Max B: " + sliderMaxB.getValue() + "|  Max G: " + sliderMaxG.getValue());
        camera.setMinRGB(new RGBValue(sliderMinR.getValue(), sliderMinB.getValue(), sliderMinG.getValue()));
        camera.setMaxRGB(new RGBValue(sliderMaxR.getValue(), sliderMaxB.getValue(), sliderMaxG.getValue()));

    }

    public final JSlider slider(int min, int max, int def, int maj, boolean ticks, boolean labels){

        JSlider myslide = new JSlider(JSlider.VERTICAL, min, max, def);
        myslide.setMajorTickSpacing(maj);
        myslide.setPaintTicks(ticks);
        myslide.setPaintLabels(labels);
        return myslide;
    }
}
