package finalProject;
 
import javafx.scene.image.Image;
public interface ICard {
    public boolean isFaceUp(); 
    public void flip();
    public void setFrontImage(Image frontImage);
    public Image getFrontImage();
    public Image getBackImage();
    public void setBackImage(Image backImage);
    public boolean equals(Object o);
    public String getValue();
    public void setValue(String value);
    public String getURL();
    public void setURL(String aURL);
}
