package com.fetch.receipt.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fetch.receipt.exception.BadRequestException;
import com.fetch.receipt.exception.NotFoundException;
import com.fetch.receipt.model.Id;
import com.fetch.receipt.model.Points;
import com.fetch.receipt.model.Receipt;
import com.fetch.receipt.service.ReceiptService;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired 
    private ReceiptService rService;
    
    @GetMapping("/{id}/points")
    public ResponseEntity<Object> getReceipts(@PathVariable String id) {
        Receipt receipt;
        String regex = "^\\S+$";

        if (id == null || id.length() != 36 || !id.toString().matches(regex)) {
            throw new BadRequestException();
        }

        try {
            receipt = rService.getReceipt(UUID.fromString(id));
            if (receipt == null) {
                throw new NotFoundException();
            }
        } catch(Exception e) {
            throw new NotFoundException();
        }
        
        Points points = rService.getPoints(receipt);

        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<Object> postReceipts(@RequestBody(required=false) Receipt receipt) {
        String regexTime = "^([01][0-9]|2[0-3]):([0-5][0-9])$";
        String regexDate = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        Pattern timePattern = Pattern.compile(regexTime);
        Pattern datePattern = Pattern.compile(regexDate);
        Matcher timeMatches = timePattern.matcher(receipt.getPurchaseTime());
        Matcher dateMatches = datePattern.matcher(receipt.getPurchaseDate());
        
        if (receipt == null ||
            !receipt.getRetailer().matches("^[\\w\\s\\-&]+$") ||
            !dateMatches.matches() ||
            !timeMatches.matches() ||
            receipt.getItems().size() < 1 ||
            !receipt.getTotal().matches("^\\d+\\.\\d{2}$")
        ) {
            throw new BadRequestException();
        }

        try {
            LocalDate.parse(receipt.getPurchaseDate());
        } catch (Exception e) {
            throw new BadRequestException();
        }

        rService.addReceipt(receipt);
        Id id = new Id(receipt.getId().toString());

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
