package engine;

public abstract class AbstractGame {

    /**
     * Extends methods that are looped through the game container.
     */
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, GameGraphics r);
}
