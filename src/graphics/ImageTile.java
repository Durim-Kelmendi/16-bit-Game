package graphics;


/**
 * Handles ImageTiles, for 2D sprites with animations.
 */
public class ImageTile extends Image {

    private int tileWidth, tileHeight;

    public ImageTile(String Path, int tileWidth, int tileHeight) {
        super(Path);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }


    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }
}
