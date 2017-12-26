package ClassObj;

/**
 *
 * @author DaBin
 */
public class Gifticon {
    int GiftId;
    String GiftDate;
    String Sender;
    String Receiver;
    String CafeName;
    String MenuName;
    int used;

    public Gifticon() {
    }
    

    public Gifticon(String Receiver, String CafeName, String MenuName) {
        this.Receiver = Receiver;
        this.CafeName = CafeName;
        this.MenuName = MenuName;
    }

    public Gifticon(int GiftId, String Receiver, String CafeName, String MenuName) {
        this.GiftId = GiftId;
        this.Receiver = Receiver;
        this.CafeName = CafeName;
        this.MenuName = MenuName;
    }
    
    
    
    public Gifticon(int GiftId, String GiftDate, String Sender, String Receiver, String CafeName, String MenuName, int used) {
        this.GiftId = GiftId;
        this.GiftDate = GiftDate;
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.CafeName = CafeName;
        this.MenuName = MenuName;
        this.used = used;
    }
    
    

    public int getGiftId() {
        return GiftId;
    }

    public void setGiftId(int GiftId) {
        this.GiftId = GiftId;
    }

    public String getGiftDate() {
        return GiftDate;
    }

    public void setGiftDate(String GiftDate) {
        this.GiftDate = GiftDate;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getCafeName() {
        return CafeName;
    }

    public void setCafeName(String CafeName) {
        this.CafeName = CafeName;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String MenuName) {
        this.MenuName = MenuName;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
    
    
}
