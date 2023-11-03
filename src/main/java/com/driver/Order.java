package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        this.id = id;
        String time[] = deliveryTime.split(":");
        int newTime = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
        this.deliveryTime = newTime;

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
