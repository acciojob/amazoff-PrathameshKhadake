package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderDatabase = new HashMap<>();
    HashMap<String, DeliveryPartner> partnerDatabase = new HashMap<>();
    HashMap<String, String> orderPartnerDB = new HashMap<>();
    HashMap<String, ArrayList<String>> partnerOrderDB = new HashMap<>();

    public void addOrder(Order order){
        orderDatabase.put(order.getId(), order);
    }

    public void addPartner(String partnerId){
        partnerDatabase.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId){

        if(orderDatabase.containsKey(orderId) && partnerDatabase.containsKey(partnerId)){
            orderPartnerDB.put(orderId, partnerId);

            ArrayList<String> currentOrders = new ArrayList<>();

            if(partnerOrderDB.containsKey(partnerId)){
                currentOrders = partnerOrderDB.get(partnerId);
            }

            currentOrders.add(orderId);
            partnerOrderDB.put(partnerId, currentOrders);

            DeliveryPartner deliveryPartner = partnerDatabase.get(partnerId);
            deliveryPartner.setNumberOfOrders(currentOrders.size());
        }
    }

    public Order getOrderById(String orderId){
        return orderDatabase.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return partnerDatabase.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return partnerOrderDB.get(partnerId).size();
    }

    public ArrayList<String> getOrdersByPartnerId(String partnerId){
        return partnerOrderDB.get(partnerId);
    }

    public ArrayList<String> getAllOrders(){
        ArrayList<String> orders = new ArrayList<>();
        for(String order : orderDatabase.keySet()){
            orders.add(order);
        }
        return orders;
    }

    public int getCountOfUnassignedOrders(){
        return orderDatabase.size() - orderPartnerDB.size();
    }

    public void deletePartnerById(String partnerId){
        partnerDatabase.remove(partnerId);

        ArrayList<String> listOfOrders = partnerOrderDB.get(partnerId);
        partnerOrderDB.remove(partnerId);

        for(String order : listOfOrders){
            orderPartnerDB.remove(order);
        }
    }

    public void deleteOrderById(String orderId){
        orderDatabase.remove(orderId);

        String partnerId = orderPartnerDB.get(orderId);
        orderPartnerDB.remove(orderId);

        partnerOrderDB.get(partnerId).remove(orderId);
        partnerDatabase.get(partnerId).setNumberOfOrders(partnerOrderDB.get(partnerId).size());
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId){
        int count = 0;

        ArrayList<String> orders = partnerOrderDB.get(partnerId);
        for(String orderId : orders){
            int deliveryTime = orderDatabase.get(orderId).getDeliveryTime();
            if(deliveryTime > time){
                count++;
            }
        }

        return count;

    }

    public int getLastDeliveryTimeByPartnerId(String partnerId){
        int maxTime = 0;
        ArrayList<String> orders = partnerOrderDB.get(partnerId);
        for(String orderId : orders){
            int currentTime = orderDatabase.get(orderId).getDeliveryTime();
            maxTime = Math.max(maxTime, currentTime);
        }
        return maxTime;
    }

}
