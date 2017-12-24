
package ClassObj;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author DaBin
 */
public class Manager {
    
    private String id;
    private String pw;
    private String name;
    private String Cafename;
    private String phonenumber;
    private String Email;

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

    public String getCafename() {
        return Cafename;
    }

    public void setCafename(String Cafename) {
        this.Cafename = Cafename;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Manager(String id, String pw, String name, String Cafename, String phonenumber, String Email) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.Cafename = Cafename;
        this.phonenumber = phonenumber;
        this.Email = Email;
    }

    
}
