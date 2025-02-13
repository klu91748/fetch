package com.fetch.receipt.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fetch.receipt.model.Receipt;

@Repository
public interface ReceiptDao extends JpaRepository<Receipt, UUID> {
    
}
