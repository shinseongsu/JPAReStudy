package com.spring.reactive.dto;

/**
 * 구매 상품 데이터
 */
public class CartItem {

    private Item item;
    private int quantity;

    public CartItem() {}

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increment() {
        this.quantity = this.quantity + 1;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
