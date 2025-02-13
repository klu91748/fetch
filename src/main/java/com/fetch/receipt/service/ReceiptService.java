package com.fetch.receipt.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fetch.receipt.dao.ReceiptDao;
import com.fetch.receipt.model.Item;
import com.fetch.receipt.model.Points;
import com.fetch.receipt.model.Receipt;

@Service
public class ReceiptService {
    
    @Autowired 
    private ReceiptDao receiptDao;

    public UUID addReceipt(Receipt receipt) {
        receiptDao.save(receipt);
        return receipt.getId();
    }

    public Receipt getReceipt(UUID id) {
        Receipt receipt = receiptDao.findById(id).orElse(null);
        return receipt;
    }

    public Points getPoints(Receipt receipt) {
        double points = 0;

        //One point for every alphanumeric character in the retailer name.
        for (char c : receipt.getRetailer().toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points++;
            }
        }

        //50 points if the total is a round dollar amount with no cents.
        double total = Double.parseDouble(receipt.getTotal());
        
        if (total % 1 == 0) {
            points += 50;
        }

        //25 points if the total is a multiple of 0.25.
        if (total % .25 == 0) {
            points += 25;
        }

        //5 points for every two items on the receipt.
        points += (receipt.getItems().size()/2) * 5;

        //If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        for (Item items : receipt.getItems()) {
            String item = items.getShortDescription().trim();
            if (item.length() % 3 == 0) {
               points += Math.ceil(Double.parseDouble(items.getPrice()) * .2);
            }
        }

        //6 points if the day in the purchase date is odd.
        int day = Integer.parseInt(receipt.getPurchaseDate().substring(8, 10));
        if (day % 2 == 1) {
            points += 6;
        }

        //10 points if the time of purchase is after 2:00pm and before 4:00pm.
        int hours = Integer.parseInt(receipt.getPurchaseTime().substring(0, 2));
        int minutes = Integer.parseInt(receipt.getPurchaseTime().substring(3, 5));
        int totalMinutes = (hours * 60) + minutes;
        if (totalMinutes > 840 && totalMinutes < 960) {
                points += 10;
        }

        return new Points((int) points);
    }
}
