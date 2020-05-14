package graphics;

public class FontHandler {

    public static final FontHandler STANDARD = new FontHandler("/sprites/font.png");
    private Image fontImage;
    private int[] offset;
    private int[] width;

    public FontHandler(String path) {
        fontImage = new Image(path);

        offset = new int[59]; //Amount of characters in the font image.
        width = new int[59];

        int unicode = 0;

        for (int i = 0; i < fontImage.getWidth(); i++) {
            if(fontImage.getPixel()[i] == 0xff0000ff) {
                offset[unicode] = i;
            }
            if(fontImage.getPixel()[i] == 0xffffff00) {
                width[unicode] = i - offset[unicode];
                unicode++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffset() {
        return offset;
    }

    public int[] getWidth() {
        return width;
    }

}
