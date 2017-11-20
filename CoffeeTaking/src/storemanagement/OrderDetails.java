/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storemanagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author YEONCHAN
 */
public class OrderDetails {

 //   private final StringProperty numb;
    private final StringProperty clientID;//회원아이디
    private final StringProperty beverage;//주문음료명
    private final StringProperty amount;//음료수량
    private final StringProperty arrTime;//도착예정시간

    public OrderDetails(String clientID, String beverage, String amount, String arrTime) {
       // this.numb = new SimpleStringProperty(numb);
        this.clientID = new SimpleStringProperty(clientID);
        this.beverage = new SimpleStringProperty(beverage);
        this.amount = new SimpleStringProperty(amount);
        this.arrTime = new SimpleStringProperty(arrTime);
    }

    public String getClientID() {
        return clientID.get();
    }

    public String getBeverage() {
        return beverage.get();
    }

    public String getAmount() {
        return amount.get();
    }

    public String getArrTime() {
        return arrTime.get();
    }

    public void setClientID(String id) {
        this.clientID.set(id);
    }

    public void setBeverage(String beverage) {
        this.beverage.set(beverage);
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public void setArrTime(String arrTime) {
        this.arrTime.set(arrTime);
    }

    public StringProperty clientIDProperty() {
        return clientID;
    }

    public StringProperty beverageProperty() {
        return beverage;
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public StringProperty arrTimeProperty() {
        return arrTime;
    }
}
