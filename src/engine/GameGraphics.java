package engine;

import graphics.FontHandler;
import graphics.Image;
import graphics.ImageTile;

import java.awt.image.DataBufferInt;

public class GameGraphics {

    /**
     * Variables.
     */
    private int pixelWidth, pixelHeight;
    private int camX, camY;
    private int[] pixels;

    private FontHandler font = FontHandler.STANDARD;

    public GameGraphics(GameContainer gc) {
        pixelHeight = gc.getHeight();
        pixelWidth = gc.getWidth();
        /**
         * Gets the pixel data without modifying the in-data. Gets the image from GameWindow.image variable.
         */
        pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    /**
     * Clears the parts that are undrawn on the screen. Without this, you will get the 'Solitaire effect'.
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    /**
     * Sets the pixel value, we also have an transparent color, which is FF00FF (Pink)
     */
    public void setPixel(int x, int y, int value) {
        if((x < 0 || x >= pixelWidth || y < 0 || y >= pixelHeight) || value == 0xffff00ff) {
            return;
        }
        pixels[x + y * pixelWidth] = value;
    }

    /**
     * Draws text on to the screen using pixel characters from image resources.
     */
    public void drawText(String text, int offsetX, int offsetY, int color) {
        text = text.toUpperCase(); //Since we only have uppercase in the image, thats only allowed.
        int offset = 0;
        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32; //Subtract by 32 to remove the first 32 unicode characters
            for (int y = 0; y < font.getFontImage().getHeight(); y++) {
                for (int x = 0; x < font.getWidth()[unicode]; x++) {
                    if(font.getFontImage().getPixel()[(x + font.getOffset()[unicode]) +
                            y * font.getFontImage().getWidth()] == 0xffffffff) {
                        setPixel(x + offsetX + offset, y + offsetY, color);
                    }
                }
            }
            offset += font.getWidth()[unicode];
        }
    }

    /**
     *
     * Draws the image on to the screen.
     */
    public void drawImage(Image image, int offsetX, int offsetY) {
        offsetX -= camX;
        offsetY -= camY;

        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();


        /**
         * Does not draw the pixels if they are off screen.
         */
        if(offsetX <- image.getWidth()) return;
        if(offsetY <- image.getHeight()) return;
        if(offsetX >= pixelWidth) return;
        if(offsetY >= pixelHeight) return;

        /**
         * Clips images each side if they are a bit cut off screen.
         */
        if (newWidth + offsetX > pixelWidth) {
            newWidth -= newWidth + offsetX - pixelWidth;
        } if (newHeight + offsetY > pixelHeight) {
            newHeight -= newHeight + offsetY - pixelHeight;
        } if(offsetX < 0) {
            newX -= offsetX;
        } if(offsetY < 0) {
            newY -= offsetY;
        }

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offsetX, y + offsetY, image.getPixel()[x + y * image.getWidth()]);
            }
        }
    }

    /**
     * Draws docked images that follow on screen
     */
    public void drawDockedImage(Image image, int offsetX, int offsetY) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                setPixel(x + offsetX, y + offsetY, image.getPixel()[x + y * image.getWidth()]);
            }
        }
    }

    /**
     * Draws docked imagetiles that follow on screen
     */
    public void drawDockedImageTile(ImageTile image, int offsetX, int offsetY, int tileX, int tileY) {
        int newWidth = image.getTileWidth();
        int newHeight = image.getTileHeight();
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                setPixel(x + offsetX, y + offsetY, image.getPixel()[(x + tileX * image.getTileWidth()) +
                        (y + tileY * image.getTileHeight()) * image.getWidth()]);
            }
        }
    }

    /**
     *
     * Draws image that has multiple tiles, used for animating.
     */
    public void drawImageTile(ImageTile image, int offsetX, int offsetY, int tileX, int tileY) {
        offsetX -= camX;
        offsetY -= camY;

        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileWidth();
        int newHeight = image.getTileHeight();

        /**
         * Does not draw the pixels if they are off screen.
         * Test by adding offset to the new height.
         */
        if(offsetX <- image.getTileWidth()) return;
        if(offsetY <- image.getTileHeight()) return;
        if(offsetX >= pixelWidth) return;
        if(offsetY >= pixelHeight) return;

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offsetX, y + offsetY, image.getPixel()[(x + tileX * image.getTileWidth()) +
                        (y + tileY * image.getTileHeight()) * image.getWidth()]);
            }
        }
    }

    public void drawFillRect(int offsetX, int offsetY, int width, int height, int color) {
        /**
         * Does not draw the pixels if they are off screen.
         */
        offsetX -= camX;
        offsetY -= camY;

        if(offsetX < -width) return;
        if(offsetY < -height) return;
        if(offsetX >= pixelWidth) return;
        if(offsetY >= pixelHeight) return;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setPixel(x + offsetX, y + offsetY, color);
            }
        }
    }

    public int getCamX() {
        return camX;
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public int getCamY() {
        return camY;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }
}
