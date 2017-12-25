/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classobj;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Leejinnyeong
 */
public class FavoriteCafe {
    private String id;
    private String cafename;
    
    /* 원래 내용
    FavoriteCafe(String id, String cafename){
        this.id = new SimpleStringProperty(id);
        this.cafename = new SimpleStringProperty(cafename);
    }
    */

    public FavoriteCafe(String id, String cafename) {
        this.id = id;
        this.cafename = cafename;
    }

    public String getId() {
        return id;
    }
    
    public String getCafeName() {
        return cafename;
    }

    public void setCafeName(String cafename) {
        this.cafename = cafename;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
