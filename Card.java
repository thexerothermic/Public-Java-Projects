package finalProject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Card extends ImageView implements ICard {

    private String url;
    private String value;
    private Image frontImage;
    private Image backImage = new Image("image/card/b2fv.png");
    private boolean faceUp;

    public boolean isFaceUp() {
        return faceUp;
    }

    //@Override
    public void flip() {
        if (isFaceUp()) {
            faceUp = false;
            this.setImage(backImage);
        } else {
            faceUp = true;
            this.setImage(frontImage);
        }

        //todo
        //flips the backImage with the front 
        //image based on the value of faceUp.
    }

    public Card() {
        super();
    }

    public Card(Image anImage) {
        super(anImage);
        //display them proportionally
        double vProp = anImage.getHeight() / anImage.getWidth();
        double hProp = anImage.getWidth() / anImage.getHeight();
        this.setFitHeight(anImage.getHeight() + (50 * vProp));
        this.setFitWidth(anImage.getHeight() + (50 * hProp));
    }

    public boolean equals(Object o) {

        if (value.equals(((Card) o).getValue())) {

            return true;
        } else {
            //todo
            //two cards are equal if their values are the same
            //be sure to implement this test correctly - see text for example
            //of the correct implementation
            return false;
        }

    }

    public String getURL() {
        return url;
    }

    /**
     * @param aURL the URL to set
     */
    public void setURL(String aURL) {
        this.url = aURL;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the frontImage
     */
    public Image getFrontImage() {
        return frontImage;
    }

    /**
     * @param frontImage the frontImage to set
     */
    public void setFrontImage(Image frontImage) {
        this.frontImage = frontImage;
    }

    /**
     * @return the backImage
     */
    public Image getBackImage() {
        return backImage;
    }

    /**
     * @param backImage the backImage to set
     */
    public void setBackImage(Image backImage) {
        this.backImage = backImage;
    }
}
