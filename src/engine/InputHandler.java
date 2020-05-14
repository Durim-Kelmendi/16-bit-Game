package engine;

import java.awt.event.*;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    private GameContainer gc;
    /**
     * Array for booleans to remove the delay when holding down a key until the next one is typed.
     * Can hold up to 256 values (Standard for keyboards). keyLastFrame and buttonsLastFrame gets information
     * from the last frame so we know if you hold down the key.
     */
    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keyLastFrame = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLastFrame = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY;

    public InputHandler(GameContainer gc) {
        this.gc = gc;
        mouseX = 0;
        mouseY = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
    }

    /**
     * Loops through constantly to check if any key is pressed.
     */
    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            keyLastFrame[i] = keys[i];
        }
        for (int i = 0; i < NUM_BUTTONS; i++) {
            buttonsLastFrame[i] = buttons[i];
        }
    }

    public boolean isKey(int keyChar) {
        return keys[keyChar];
    }

    public boolean isKeyUp(int keyChar) {
        return !keys[keyChar] && keyLastFrame[keyChar];
    }

    public boolean isKeyDown(int keyChar) {
        return keys[keyChar] && !keyLastFrame[keyChar];
    }

    public boolean isButton(int button) {
        return buttons[button];
    }

    public boolean isButtonUp(int button) {
        return !buttons[button] && buttonsLastFrame[button];
    }

    public boolean isButtonDown(int button) {
        return buttons[button] && !buttonsLastFrame[button];
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        buttons[mouseEvent.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        buttons[mouseEvent.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = (int)(mouseEvent.getX());
        mouseY = (int)(mouseEvent.getY());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = (int)(mouseEvent.getX());
        mouseY = (int)(mouseEvent.getY());
    }

}
