package com.fetch.receipt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Receipt {
    
    @Id
    @GeneratedValue(strategy=GenerationType.UUID) 
    private UUID id;
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private String total;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Item> items;
    
    public Receipt() {

    }

    public Receipt(String retailer, String purchaseDate, String purchaseTime, String total, List<Item> items) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.total = total;
        this.items = new ArrayList<>();
    }

    public UUID getId() {
		return id;
	}

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
 