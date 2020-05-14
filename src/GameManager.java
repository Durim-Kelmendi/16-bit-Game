import engine.AbstractGame;
import engine.GameContainer;
import engine.GameGraphics;
import graphics.Image;
import graphics.ImageTile;


import java.util.ArrayList;

public class GameManager extends AbstractGame {

    public static final int TILE_SIZE = 16;
    private Image levelImage = new Image("/levels/map.png");
    private Image healthBar = new Image("/player/health.png");

    private CameraHandler camera;

    private boolean[] collision;
    private boolean[] first_chest;
    private boolean[] second_chest;
    private boolean[] third_chest;
    private boolean[] lava;
    private boolean[] water;    //TODO: Water movements.
    private boolean[] checkpoint;

    private int levelWidth, levelHeight;

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    /**
     * Creates an abstract game class, which separates the game from the game engine.
     */
    public GameManager() {
        loadLevel("/levels/collisionmap.png");
        objects.add(new Player(4, 8));
        //camera = new CameraHandler("Player");
    }

    @Override
    public void update(GameContainer gc, float dt) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update(gc, this, dt);
            if(objects.get(i).isDeadObject()) {
                objects.remove(i);
                i--;
            }
        }
        //camera.update(gc, this, dt);
    }

    @Override
    public void render(GameContainer gc, GameGraphics r) {
        //camera.render(r);
        r.drawImage(levelImage, 0, 0);
        /**
         * Draws out the collision map.
         */
        /**for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                if(collision[x + y * levelWidth]) {
                    r.drawFillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 0xff182c3b);
                } else {
                    r.drawFillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 0xfff9f9f9);
                }
                if(chest[x + y * levelWidth]) {
                    r.drawFillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 0xff00ff00);
                }
                if(lava[x + y * levelWidth]) {
                    r.drawFillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 0xffff0000);
                }
            }
        }*/
        for(GameObject obj: objects) {
           obj.render(gc, r);
        }
    }

    /**
     * Checks if you are running in to a black box on the collision map. Returns true if you are.
     */
    public boolean getCollision(int x, int y) {
        if(x < 0 || x >= levelWidth || y < 0 || y >= levelHeight) {
            return true;
        }
        return collision[x + y * levelWidth];
    }

    /**
     * Checks if you are in a chestarea (0xF00FF00), returns true if you are.
     */
    public boolean getFirstChestArea(int x, int y) {
        if(x < 0 || x >= levelWidth || y < 0 || y >= levelHeight) {
            return true;
        }
        return first_chest[x + y * levelWidth];
    }
    public boolean getSecondChestArea(int x, int y) {
        if(x < 0 || x >= levelWidth || y < 0 || y >= levelHeight) {
            return true;
        }
        return second_chest[x + y * levelWidth];
    }
    public boolean getThirdChestArea(int x, int y) {
        if(x < 0 || x >= levelWidth || y < 0 || y >= levelHeight) {
            return true;
        }
        return third_chest[x + y * levelWidth];
    }
    /**
     * Checks if you are in lava area (0xFFF0000). Returns true if you are.
     */
    public boolean getLavaArea(int x, int y) {
        if(x < 0 || x >= levelWidth || y < 0 || y >= levelHeight) {
            return true;
        }
        return lava[x + y * levelWidth];
    }

    public boolean getCheckpointArea(int x, int y) {
        if(x < 0 || x >= levelWidth || y < 0 || y >= levelHeight) {
            return true;
        }
        return checkpoint[x + y * levelWidth];
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public GameObject removeObject(String tag) {
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).isDeadObject()) {
                objects.remove(i);
                i--;
            }
        }
        return null;
    }

    public GameObject getObject(String tag) {
        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getTag().equals(tag)) {
                    return objects.get(i);

            }
        }
        return null;
    }

    /**
     * This loads the collision map. It loops all pixels in the image file and checks the colors of all the pixels
     * in the tilemap. It returns true on the correct corresponding color so we know where the collision map, chest,
     * lava and water areas are.
     */
    public void loadLevel(String path) {
        Image levelImage = new Image(path);
        levelWidth = levelImage.getWidth();
        levelHeight = levelImage.getHeight();
        collision = new boolean[levelWidth * levelHeight];
        first_chest = new boolean[levelWidth * levelHeight];
        second_chest = new boolean[levelWidth * levelHeight];
        third_chest = new boolean[levelWidth * levelHeight];
        checkpoint = new boolean[levelWidth * levelHeight];
        lava = new boolean[levelWidth * levelHeight];
        for (int y = 0; y < levelImage.getHeight(); y++) {
            for (int x = 0; x < levelImage.getWidth(); x++) {
                if(levelImage.getPixel()[x + y * levelImage.getWidth()] == 0xff000000) {
                    collision[x + y * levelImage.getWidth()] = true;
                } else if(levelImage.getPixel()[x + y * levelImage.getWidth()] == 0xff00ff00) {
                    first_chest[x + y * levelImage.getWidth()] = true;
                } else if(levelImage.getPixel()[x + y * levelImage.getWidth()] == 0xff00ff01) {
                    second_chest[x + y * levelImage.getWidth()] = true;
                } else if(levelImage.getPixel()[x + y * levelImage.getWidth()] == 0xff00ff02) {
                    third_chest[x + y * levelImage.getWidth()] = true;
                }else if(levelImage.getPixel()[x + y * levelImage.getWidth()] == 0x00000000) {
                    checkpoint[x + y * levelImage.getWidth()] = true;}
                else if (levelImage.getPixel()[x + y * levelImage.getWidth()] == 0xffff0000) {
                    lava[x + y * levelImage.getWidth()] = true;
                } else {
                    collision[x + y * levelImage.getWidth()] = false;
                    first_chest[x + y * levelImage.getWidth()] = false;
                }
            }
        }
    }

    /**
     * Main method starts the game window with low resolution upscaled 4 times its size (4f is float).
     */
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(200);
        gc.setScale(4f);
        gc.start();
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public static class NPCHandler extends GameObject {

        private ImageTile enemy = new ImageTile("/player/player.png", 32, 32);
        private int tileX, tileY;
        private float offsetX, offsetY;
        private int padding, paddingTop;
        private int direction = 0;
        private float animation = 0;

        public NPCHandler(int posX, int posY) {
            this.tag = "NPC";
            this.posX = posX;
            this.posY = posY;
            this.tileX = posX;
            this.tileY = posY;
            this.offsetX = 0;
            this.offsetY = 0;
            this.posX = posX * 32;
            this.posY = posY * 32;
            this.width = 32;
            this.height = 32;
            this.animation = 0;
            this.direction = 0;
            padding = 8;
            paddingTop = 2;
        }

        @Override
        public void update(GameContainer gc, GameManager gm, float dt) {
            if(animation < 14){
                animation++;}
            else{animation = 0;}
            if(gm.getCollision(tileX, tileY)) {
                System.out.println("GHAUJG");
                this.deadObject = true;
            }
            posX = tileX * TILE_SIZE + offsetX;
            posY = tileY * TILE_SIZE + offsetY;
        }

        @Override
        public void render(GameContainer gc, GameGraphics r) {
            r.drawImageTile(enemy, (int)posX, (int)posY, (int)animation, direction);
        }
    }
}
