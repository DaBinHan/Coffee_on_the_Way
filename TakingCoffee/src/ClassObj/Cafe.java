package ClassObj;

/**
 *
 * @author DaBinHan
 */
public class Cafe {

    private String cafename;
    private String uniname;
    private String addr;
    private String phonenumber;
    private String managerid;
    private int postpayavail;

    public Menu Menu = new Menu(this.cafename);

    public Cafe(String cafename) {
        this.cafename = cafename;
    }

    public Cafe(String cafename, String uniname, String addr, String phonenumber, String managerid, int postpayavail) {
        this.cafename = cafename;
        this.uniname = uniname;
        this.addr = addr;
        this.phonenumber = phonenumber;
        this.managerid = managerid;
        this.postpayavail = postpayavail;
    }

    public Cafe(String cafename, String uniname, String addr, int postpayavail) {
        this.cafename = cafename;
        this.uniname = uniname;
        this.addr = addr;
        this.postpayavail = postpayavail;
    }

    public String getCafename() {
        return cafename;
    }

    public void setCafename(String cafename) {
        this.cafename = cafename;
    }

    public String getUniname() {
        return uniname;
    }

    public void setUniname(String uniname) {
        this.uniname = uniname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public int getPostpayavail() {
        return postpayavail;
    }

    public void setPostpayavail(int postpayavail) {
        this.postpayavail = postpayavail;
    }

}
