import engine.GameContainer;
import engine.GameGraphics;

public abstract class GameObject {

    protected String tag;
    protected float posX, posY;
    protected int width, height;
    protected int health = 100;

    protected boolean deadObject = false;

    public abstract void update(GameContainer gc, GameManager gm, float dt);
    public abstract void render(GameContainer gc, GameGraphics r);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDeadObject() {
        return deadObject;
    }

    public void setDeadObject(boolean deadObject) {
        this.deadObject = deadObject;
    }
}
