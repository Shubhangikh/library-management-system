package com.utd.core.library.model;

import java.io.Serializable;

public class Borrower implements Serializable{
    private static final long serialVersionUID = 5L;

    private String cardId;
    private String ssn;
    private String bName;
    private String address;
    private String phone;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "cardId=" + cardId +
                ", ssn='" + ssn + '\'' +
                ", bName='" + bName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


}
