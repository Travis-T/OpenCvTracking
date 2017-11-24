package me.travis.camera.utils;

import org.opencv.core.Scalar;

public enum Colors {
    RED(new Scalar(255, 0, 0)), BLUE(new Scalar(0, 0, 255)), GREEN(new Scalar(0, 128, 0)), YELLOW(new Scalar(255, 255, 0));

    private Scalar color;
    Colors(Scalar color) {
        this.color = color;
    }

    public Scalar get() {
        return color;
    }

}