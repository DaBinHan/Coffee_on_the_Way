package ClassObj;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author DaBin
 */
public class Consumer {

    private String id;
    private String pw;
    private String name;
    private String phonenumber;
    private String uni;
    private String Email;
    private String BeanAmount;

    public Consumer(String id, String pw, String name, String phonenumber, String uni, String Email, String BeanAmount) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phonenumber = phonenumber;
        this.uni = uni;
        this.Email = Email;
        this.BeanAmount = BeanAmount;
    } 
   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getBeanAmount() {
        return BeanAmount;
    }

    public void setBeanAmount(String BeanAmount) {
        this.BeanAmount = BeanAmount;
    }
    
    
    
    
  
}
