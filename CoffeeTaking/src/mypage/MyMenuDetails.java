/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Leejinnyeong
 */
public class MyMenuDetails {
    private final StringProperty id;
    private final StringProperty cafename;
    private final StringProperty menuname;
    private final StringProperty materialquantity;
    // id name price 에 해당하는 내용이 scenebuilder에 있으니깐.

    public MyMenuDetails(String id, String cafename, String menuname, String materialquantity) {
        this.id = new SimpleStringProperty(id);
        this.cafename = new SimpleStringProperty(cafename);
        this.menuname = new SimpleStringProperty(menuname);
        this.materialquantity = new SimpleStringProperty(materialquantity);
    }

    // get, set 메소드 만들기
    
    public String getId() {
        return id.get();
    }

    public String getCafename() {
        return cafename.get();
    }
    
    public String getMenuname() {
        return menuname.get();
    }

    public String getMaterialquantity() {
        return materialquantity.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setCafename(String cafename) {
        this.cafename.set(cafename);
    }
    
    public void setMenuname(String menuname) {
        this.menuname.set(menuname);
    }

    public void setPrice(String materialquantity) {
        this.materialquantity.set(materialquantity);
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty cafenameProperty() {
        return cafename;
    }
    
    public StringProperty menunameProperty() {
        return menuname;
    }

    public StringProperty MaterialquantityProperty() {
        return materialquantity;
    }
}
