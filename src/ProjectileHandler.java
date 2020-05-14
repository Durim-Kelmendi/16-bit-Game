import engine.GameContainer;
import engine.GameGraphics;
import graphics.Image;

public class ProjectileHandler extends GameObject {

    private Image arrow = new Image("/weapon/arrow.png");

    private int tileX, tileY;
    private float offsetX, offsetY;
    private float speed = 200;
    private int direction;

    public ProjectileHandler(int tileX, int tileY, float offsetX, float offsetY, int direction) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.direction = direction;
        posX = tileX * GameManager.TILE_SIZE + offsetX;
        posY = tileY * GameManager.TILE_SIZE + offsetY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        switch (direction) {
            case 0:
                offsetX += speed * dt;
                break;
            case 1:
                offsetX -= speed * dt;
                break;
        }

        if(offsetY > GameManager.TILE_SIZE) {
            tileY++;
            offsetY -= GameManager.TILE_SIZE;
        }
        if(offsetY < 0) {
            tileY--;
            offsetY += GameManager.TILE_SIZE;
        }

        if(offsetX > GameManager.TILE_SIZE)  {
            tileX++;
            offsetX -= GameManager.TILE_SIZE;
        }
        if(offsetX < 0) {
            tileX--;
            offsetX += GameManager.TILE_SIZE;
        }

        if(gm.getCollision(tileX, tileY)) {
            this.deadObject = true;
        }

        posX = tileX * GameManager.TILE_SIZE + offsetX;
        posY = tileY * GameManager.TILE_SIZE + offsetY;
    }

    @Override
    public void render(GameContainer gc, GameGraphics r) {
        //r.drawFillRect((int)posX, (int)posY, 4, 4, 0xffff0000);
        r.drawImage(arrow, (int)posX, (int)posY);
    }
}
