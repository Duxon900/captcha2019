package captcha2019;

import javafx.scene.image.Image;

import java.util.Date;

public class Captcha {
    private int id;
    private String filename;
    private String value;
    private long date;
    private String data;
    private Image irudia;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Image getIrudia() {
        return irudia;
    }

    public void setIrudia(Image irudia) {
        this.irudia = irudia;
    }
}
