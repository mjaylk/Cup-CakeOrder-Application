package com.blackcode.cupcakefactory;

public class Card {
    private String holderName;
    private String cardNumber;
    private String year;

    public Card(String holderName, String cardNumber, String year) {
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.year = year;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
