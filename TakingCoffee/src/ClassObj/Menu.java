package ClassObj;

/**
 *
 * @author DaBin
 */
public class Menu { // Cafe 클래스의 하위 클래스
    int id;
    String MenuName;
    String CafeName;
    String Price;
    String op; // 매니저가 적어놓은 option

    public Menu(int id, String MenuName, String CafeName, String Price, String op) {
        this.id = id;
        this.MenuName = MenuName;
        this.CafeName = CafeName;
        this.Price = Price;
        this.op = op;
    }

    public Menu(String CafeName) {
        this.CafeName = CafeName;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String MenuName) {
        this.MenuName = MenuName;
    }

    public String getCafeName() {
        return CafeName;
    }

    public void setCafeName(String CafeName) {
        this.CafeName = CafeName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
    
    
    
    
}
