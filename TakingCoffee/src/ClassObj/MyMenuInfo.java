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
public class MyMenuInfo {
    private String cafename;
    private String menuname;
    private String option;
    
    public MyMenuInfo(String cafename, String menuname, String option) {
            this.cafename = cafename;
            this.menuname = menuname;
            this.option = option;
        }

    public String getCafename() {
        return cafename;
    }

    public String getMenuname() {
        return menuname;
    }

    public String getOption() {
        return option;
    }
    
    public void setCafeName(String cafename) {
        this.cafename = cafename;
    }
    
    public void setMenuName(String menuname) {
        this.menuname = menuname;
    }

    public void setOption(String option) {
        this.option = option;
    }    
}
