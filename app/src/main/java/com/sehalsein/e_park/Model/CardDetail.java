package com.sehalsein.e_park.Model;

/**
 * Created by sehalsein on 08/11/17.
 */

public class CardDetail {

    private String id;
    private String cardNo;
    private String cardName;
    private String cvvNo;
    private String expiryDate;




    public CardDetail() {
    }


    public String getCardName() {
        return cardName;
    }

    public CardDetail(String id, String cardNo, String cardName, String cvvNo, String expiryDate) {
        this.id = id;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.cvvNo = cvvNo;
        this.expiryDate = expiryDate;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCvvNo() {
        return cvvNo;
    }

    public void setCvvNo(String cvvNo) {
        this.cvvNo = cvvNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
