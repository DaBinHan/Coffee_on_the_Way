//
package review;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author User
 */
public class Review_Detail {

    private final StringProperty id;
    private final StringProperty cafe;
    private final StringProperty title;
    private final StringProperty text;

    public Review_Detail(String id, String cafe, String title, String text) {
        this.id = new SimpleStringProperty(id);
        this.cafe = new SimpleStringProperty(cafe);
        this.title = new SimpleStringProperty(title);
        this.text = new SimpleStringProperty(text);
    }

    public String getId() {
        return id.get();
    }

    public String getCafe() {
        return cafe.get();
    }
   
    public String getTitle() {
        return title.get();
    }

    public String getText() {
        return text.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setCafe(String cafe) {
        this.cafe.set(cafe);
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setText(String text) {
        this.text.set(text);
    }
    

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty cafeProperty() {
        return cafe;
    }
    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty textProperty() {
        return text;
    }

}