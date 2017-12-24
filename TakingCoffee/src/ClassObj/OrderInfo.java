package ClassObj;

/**
 *
 * @author YEONCHAN
 */
public class OrderInfo {

    private int orderid;
    private String consumerid;
    private String Cafename;
    private String menuname;
    private String amount;
    private String arrTime;
    private String paymentType;
    private int menucomplete;
    private int menureceipt;
    private String op;

    public OrderInfo(int orderid, String consumerid, String Cafename, String menuname, String amount, String arrTime, String paymentType, int menucomplete, int menureceipt, String option) {
        this.orderid = orderid;
        this.consumerid = consumerid;
        this.Cafename = Cafename;
        this.menuname = menuname;
        this.amount = amount;
        this.arrTime = arrTime;
        this.paymentType = paymentType;
        this.menucomplete = menucomplete;
        this.menureceipt = menureceipt;
        this.op = option;
    }

  
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getConsumerid() {
        return consumerid;
    }

    public void setConsumerid(String consumerid) {
        this.consumerid = consumerid;
    }

    public String getCafename() {
        return Cafename;
    }

    public void setCafename(String Cafename) {
        this.Cafename = Cafename;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getMenucomplete() {
        return menucomplete;
    }

    public void setMenucomplete(int menucomplete) {
        this.menucomplete = menucomplete;
    }

    public int getMenureceipt() {
        return menureceipt;
    }

    public void setMenureceipt(int menureceipt) {
        this.menureceipt = menureceipt;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String option) {
        this.op = option;
    }


    
    
    
}
