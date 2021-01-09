package captcha2019;

import javafx.scene.image.Image;

public class Captcha {
    private int id;
    private String filename;
    private String value;
    private Integer date;
    private Image irudia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Image getIrudia() {
        return irudia;
    }

    public void setIrudia(Image irudia) {
        this.irudia = irudia;
    }
}
