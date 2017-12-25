/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassObj;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Leejinnyeong
 */
public class ChargeInfo {
    
    private String chargedate;
    private String chargeamount;
    private String beanbefore;
    private String beanafter;
    private String depositcheck;
    
    public ChargeInfo(String chargedate, String chargeamount, String beanbefore, String beanafter, String depositcheck) {
        this.chargedate =  chargedate;
        this.chargeamount =  chargeamount;
        this.beanbefore =  beanbefore;
        this.beanafter =  chargeamount+beanbefore;  //chargeamount+beanbefore;
        this.depositcheck =  depositcheck;
        }
    
    public String getChargedate() {
            return chargedate;
        }

        public String getChargeamount() {
            return chargeamount;
        }

        public String getBeanbefore() {
            return beanbefore;
        }

        public String getBeanafter() {
            return beanafter;
        }

        public String getDepositcheck() {
            return depositcheck;
        }

    public void setChargedate(String chargedate) {
        this.chargedate = chargedate;
    }

    public void setChargeamount(String chargeamount) {
        this.chargeamount = chargeamount;
    }

    public void setBeanbefore(String beanbefore) {
        this.beanbefore = beanbefore;
    }

    public void setBeanafter(String beanafter) {
        this.beanafter = beanafter;
    }

    public void setDepositcheck(String depositcheck) {
        this.depositcheck = depositcheck;
    }       
}