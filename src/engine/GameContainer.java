package engine;


public class GameContainer implements Runnable {

    private Thread thread;
    private GameWindow window;
    private GameGraphics gameRender;
    private InputHandler input;
    private AbstractGame game;

    private boolean running = false;
    private final double UPDATE_CAP = 1.0/60.0; //Sets the game to 60 FPS.
    private int width = 320;
    private int height = 240;
    private float scale = 4f;
    private String title = "Exodia";

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public GameGraphics getGameRender() {
        return gameRender;
    }

    /**
     * Starts a thread
     */
    public void start() {
        window = new GameWindow(this);
        gameRender = new GameGraphics(this);
        input = new InputHandler(this);

        thread = new Thread(this);
        thread.run();
    }

    /**
     * Stops thread.
     */
    public void stop() {

    }

    /**
     * Runs a thread for software based rendering (CPU). Frames is set to 60FPS.
     * Controllers makes sure that the frames are not
     */
    public void run() {
        running = true;

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double deltaTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while(running) {
            render = false;

            firstTime = System.nanoTime() / 1000000000.0;
            deltaTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += deltaTime;
            frameTime += deltaTime;

            /**
             * Render only if the client updates.
             */
            while(unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;
                game.update(this, (float)UPDATE_CAP);
                input.update();
                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }
            /**
             * Here is all the main content being looped. The render and update method are in AbstractGame that can
             * be extended. They are then automatically looped through this method.
             */
            if(render) {
                gameRender.clear();
                game.render(this, gameRender);
                //gameRender.drawText("FPS: " + fps, 0, 0, 0xff00ffff);
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //TODO: Maybe fix this is errors occurs, nothing tested hard yet.
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    /**
     * Disposes the run method to close the thread properly.
     */
    public void dispose() {

    }

    /**
     * Getters and Setters
     */
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public GameWindow getWindow() {
        return window;
    }

    public InputHandler getInput() {
        return input;
    }

    public float getScale() {
        return scale;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
