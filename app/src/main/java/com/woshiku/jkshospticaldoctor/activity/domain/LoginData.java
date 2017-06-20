package com.woshiku.jkshospticaldoctor.activity.domain;

/**
 * Created by admin on 2017-04-21.
 */

public class LoginData {
    private String userTicket;
    private String userPass;

    public LoginData(String userTicket, String userPass) {
        this.userTicket = userTicket;
        this.userPass = userPass;
    }

    public String getUserTicket() {
        return userTicket;
    }

    public void setUserTicket(String userTicket) {
        this.userTicket = userTicket;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
