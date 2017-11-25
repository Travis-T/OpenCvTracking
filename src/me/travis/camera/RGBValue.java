package me.travis.camera;

//Stores rbh values because im too fucked to use an array
public class RGBValue {

    private int r, b, g;

    /**
     * @param r     Red value
     * @param b     Blue Value
     * @param g     Green Value
     */
    public RGBValue(int r, int b, int g) {
        this.r = r;
        this.b = b;
        this.g = g;
    }

    /**
     * Sets the red value.
     * @param r The new red value
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * Sets the blue value.
     * @param b The new blue value
     */
    public void setB(int b) {
        this.b = b;
    }

    /**
     * Sets the green value.
     * @param g The green value
     */
    public void setG(int g) {
        this.g = g;
    }

    /**
     * Gets the red value.
     * @return  The red value
     */
    public int getR() {
        return r;
    }

    /**
     * Gets the blue value.
     * @return  The blue value
     */
    public int getB() {
        return b;
    }

    /**
     * Gets the green value.
     * @return  The green value
     */
    public int getG() {
        return g;
    }
}
