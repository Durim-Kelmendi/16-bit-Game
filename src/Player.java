import engine.GameContainer;
import engine.GameGraphics;
import graphics.ImageTile;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

    private ImageTile player = new ImageTile("/player/player.png", 32, 32);
    private ImageTile keysImage = new ImageTile("/levels/keys.png", 39, 17);

    private int direction = 0;
    private float animation = 0;
    private int tileX, tileY;
    private float offsetX, offsetY;
    private int padding, paddingTop;
    private CameraHandler camera;
    private boolean firstKeyAcquired;
    private boolean secondKeyAcquired;
    private boolean thirdKeyAcquired;
    private float speed = 100;
    private float fallDistance = 0;
    private float fallSpeed = 10;
    private float jump = -5;
    private boolean ground = false;
    private int keys = 0;
    private int respawnX = 0;
    private int respawnY = 0;
    List<Integer> keyList = new ArrayList<Integer>();


    public Player(int posX, int posY) {
        this.tag = "Player";
        this.tileX = posX;
        this.tileY = posY;
        this.offsetX = 0;
        this.offsetY = 0;
        this.posX = posX * 32;
        this.posY = posY * 32;
        this.width = 32;
        this.height = 32;
        this.respawnX = 250;
        this.respawnY = 8;
        this.firstKeyAcquired = false;
        this.secondKeyAcquired = false;
        this.thirdKeyAcquired = false;
        padding = 8;
        paddingTop = 2;
        camera = new CameraHandler("Player");
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if(health <= 0) {
            if (gc.getInput().isKey(KeyEvent.VK_R)) {
                this.tileX = respawnX;
                this.tileY = respawnY;
                //this.deadObject = true;
                health = 100;
                camera.update(gc, gm, dt);
            }
        } else {
            /**
             * Input keys contains clipping parameters so we don't clip when falling off a block
             * then walking back up to it.
             */
            if (gc.getInput().isKey(KeyEvent.VK_D)) {
                if (gm.getCollision(tileX + 2, tileY) || gm.getCollision(tileX + 2,
                        tileY + (int) Math.signum((int) offsetY)) || gm.getCollision(tileX + 2, tileY + 1) ||
                        gm.getCollision(tileX + 2, tileY + (int) Math.signum((int) offsetY) + 1)) {
                    offsetX += dt * speed;
                    if (offsetX > padding) {
                        offsetX = padding;
                    }
                } else {
                    offsetX += dt * speed;
                }
            }

            if (gc.getInput().isKey(KeyEvent.VK_A)) {
                if (gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1,
                        tileY + 1 + (int) Math.signum((int) offsetY)) || gm.getCollision(tileX - 1, tileY + 1) ||
                        gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offsetY) + 1)) {
                    offsetX -= dt * speed;
                    if (offsetX < -padding) {
                        offsetX = -padding;
                    }
                } else {
                    offsetX -= dt * speed;
                }
            }
            if (gm.getLavaArea(tileX, tileY)) {
                System.out.println(health);
                health -= dt;
            }
            if (gm.getCheckpointArea(tileX, tileY)) {
                System.out.println("CHECKPOINT");
                this.respawnX = tileX;
                this.respawnY = tileY;
            }
            /**
             * Jump with clipping.
             */
            fallDistance += dt * fallSpeed;
            offsetY += fallDistance;
            if (gc.getInput().isKeyDown(KeyEvent.VK_SPACE) && ground) {
                fallDistance = jump;
                ground = true;
            }
            //Int in signum removes double, so your character can fall through blocks that are the same size.
            if (fallDistance < 0) {
                if ((gm.getCollision(tileX + 1, tileY - 1) ||
                        gm.getCollision(tileX + (int) Math.signum((int) Math.abs(offsetX) > padding ? offsetX : 0) + 1,
                                tileY - 1)) && offsetY < -paddingTop) {
                    fallDistance = 0;
                    offsetY = 0;
                }
            }
            if (fallDistance > 0) {
                if ((gm.getCollision(tileX, tileY + 2) || gm.getCollision(tileX +
                                (int) Math.signum((int) Math.abs(offsetX) > padding ? offsetX : 0),
                        tileY + 2)) && offsetY > 0 || (gm.getCollision(tileX + 1, tileY + 2) ||
                        gm.getCollision(tileX + 1 + (int) Math.signum((int) Math.abs(offsetX) > padding ? offsetX : 0),
                                tileY + 2)) && offsetY > 0) {
                    fallDistance = 0;
                    offsetY = 0;
                    ground = true;
                }
            }
            if (offsetY > GameManager.TILE_SIZE / 2) {
                tileY++;
                offsetY -= GameManager.TILE_SIZE;
            }
            if (offsetY < -GameManager.TILE_SIZE / 2) {
                tileY--;
                offsetY += GameManager.TILE_SIZE;
            }

            if (offsetX > GameManager.TILE_SIZE / 2) {
                tileX++;
                offsetX -= GameManager.TILE_SIZE;
            }
            if (offsetX < -GameManager.TILE_SIZE / 2) {
                tileX--;
                offsetX += GameManager.TILE_SIZE;
            }
            posX = tileX * GameManager.TILE_SIZE + offsetX;
            posY = tileY * GameManager.TILE_SIZE + offsetY;


            /**
             * Animations and keybindings.
             */
            // Shooting projectile is seperate so you can shoot while doing any other animation.
            if (gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
                gm.addObject(new ProjectileHandler(tileX, tileY, offsetX + width / 2, offsetY + height / 2, direction));
            }
            if (gc.getInput().isKey(KeyEvent.VK_D)) {
                direction = 0;
                animation += dt * 11;
                if (animation <= 4 || animation >= 12) {
                    animation = 4;
                }
            } else if (gc.getInput().isKey(KeyEvent.VK_A)) {
                direction = 1;
                animation += dt * 11;
                if (animation <= 4 || animation >= 12) {
                    animation = 4;
                }
            } else if (gc.getInput().isKey(KeyEvent.VK_CONTROL)) {
                animation += dt * 3;
                if (animation <= 17 || animation >= 20) {
                    animation = 17;
                }
            } else if (gc.getInput().isKeyUp(KeyEvent.VK_E)) {

                if (gm.getFirstChestArea(tileX, tileY)) {
                    for (Integer integer : keyList) {
                        if(integer == 1){firstKeyAcquired = true;}
                    }
                    if(!firstKeyAcquired){
                    keyList.add(1);
                    keys++;}
                }
                if (gm.getSecondChestArea(tileX, tileY)) {
                    for (Integer integer : keyList) {
                        if(integer == 2){secondKeyAcquired = true;}
                    }
                    if(!secondKeyAcquired){
                    keyList.add(2);
                    keys++;}
                }
                if (gm.getThirdChestArea(tileX, tileY)) {
                    for (Integer integer : keyList) {
                        if(integer == 2){thirdKeyAcquired = true;}}
                    if(!thirdKeyAcquired){
                    keyList.add(3);
                    keys++;}
                }
            } else {
                animation += dt * 3;
                if (animation >= 4) {
                    animation = 0;
                }
            }
            if (fallDistance > 0) {
                animation = 13;
            } else if (fallDistance < 0) {
                animation = 15;
            }
        }
        camera.update(gc, gm, dt);
    }

    @Override
    public void render(GameContainer gc, GameGraphics r) {
        camera.render(r);
        if(health <= 0) {
            r.drawText("You died!", 141, 80, 0xffff0000);
            r.drawText("Press R to respawn", 120, 100, 0xffffffff);
        } else {
            r.drawImageTile(player, (int)posX, (int)posY, (int)animation, direction);
            r.drawText("Health: " + health, 0, 0, 0xff00ffff);
            r.drawDockedImageTile(keysImage, 1, 7, keys, 0);
        }
    }

    public int getKeys() {
        return keys;
    }
}
