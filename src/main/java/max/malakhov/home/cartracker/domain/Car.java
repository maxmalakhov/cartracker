package max.malakhov.home.cartracker.domain;

import org.joda.time.DateTime;

/**
 * @author Max Malakhov <malakhovbox@gmail.com>
 * @version 0.1
 * @since 2013-02-17
 */
public class Car {

    private String id;
    private String packag;
    private String engine;
    private String number;
    private Color color;
    private String price;
    private String title;
    private DateTime update;

    public Car(String id, String packag, String engine, String number, String colorCode, String colorName, String price, String title) {
        this.id = id;
        this.packag = packag;
        this.engine = engine;
        this.number = number;
        this.color = new Color(colorCode, colorName);
        this.price = price;
        this.title = title;
        this.update = DateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackag() {
        return packag;
    }

    public void setPackag(String packag) {
        this.packag = packag;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getUpdate() {
        return update;
    }

    public void setUpdate(DateTime update) {
        this.update = update;
    }

    private class Color {

        private String code;
        private String name;

        public Color(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
