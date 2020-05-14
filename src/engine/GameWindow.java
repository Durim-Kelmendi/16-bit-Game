package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * This file contains all the properties that sets to the game window.
 */
public class GameWindow {

    private JFrame frame;
    private BufferedImage image; //Image data stored in the random access memory.
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    /**
     * Properties to the frame.
     */
    public GameWindow(GameContainer gc) {
        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        Dimension dimension = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));

        canvas = new Canvas();
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMinimumSize(dimension);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);

        canvas.createBufferStrategy(2); // Amount of buffers to render to.
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
    }

    /**
     * Updates the frame.
     */
    public void update() {
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

}
