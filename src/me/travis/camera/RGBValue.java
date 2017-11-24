package me.travis.camera;

public class RGBValue {

    private int r, b, g;
    public RGBValue(int r, int b, int g) {
        this.r = r;
        this.b = b;
        this.g = g;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }
}
