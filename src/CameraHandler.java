import engine.GameContainer;
import engine.GameGraphics;

public class CameraHandler {

    private float offsetX, offsetY;
    private String targetTag;
    private GameObject target = null;

    public void update(GameContainer gc, GameManager gm, float dt) {
        if(target == null) {
            target = gm.getObject(targetTag);
        }
        if(target == null) {
            return;
        }
        float targetX = (target.getPosX() + target.getWidth() / 2) - gc.getWidth() / 2;
        float targetY = (target.getPosY() + target.getHeight() / 2) - gc.getHeight() / 2;

        /**
         * Camera follow smoothness.
         */
        offsetX -= dt * (offsetX - targetX) * 7;
        offsetY -= dt * (offsetY - targetY) * 7;

        /**
         * Make sure camera doesn't go out of bounds from the map.
         */
        if(offsetX < 0) {
            offsetX = 0;
        }
        if(offsetY < 0) {
            offsetY = 0;
        }
        if(offsetX + gc.getWidth() > gm.getLevelWidth() * GameManager.TILE_SIZE) {
            offsetX = gm.getLevelWidth() * GameManager.TILE_SIZE - gc.getWidth();
        }
        if(offsetY + gc.getHeight() > gm.getLevelHeight() * GameManager.TILE_SIZE) {
            offsetY = gm.getLevelHeight() * GameManager.TILE_SIZE - gc.getHeight();
        }
        gc.getGameRender().setCamX((int)offsetX);
        gc.getGameRender().setCamY((int)offsetY);
    }

    public void render(GameGraphics gameRender) {
        gameRender.setCamX((int)offsetX);
        gameRender.setCamY((int)offsetY);
    }

    public CameraHandler(String tag) {
        this.targetTag = tag;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }
}
