package com.blackcode.cupcakefactory;

public class Cart {
    private String itemName;
    private String itemCount;
    private String itemTotal;
    private String itemDesc;
    private String itemImage;
    private String singlePrice;

    public Cart(String itemName, String itemCount, String itemTotal, String itemDesc, String itemImage, String singlePrice ) {
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemTotal = itemTotal;
        this.itemDesc = itemDesc;
        this.itemImage = itemImage;
        this.singlePrice = singlePrice;
    }

    public Cart() {
    }

    public String getSinglePrice() {
        return singlePrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCount() {
        return itemCount;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public String getItemDesc() {
        return itemDesc;
    }
}