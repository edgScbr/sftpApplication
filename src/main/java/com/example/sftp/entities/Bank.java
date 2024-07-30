package com.example.sftp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.Link;

@Data
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord( separator = "," )
@Link
public class Bank {

    private String cardExpire;
    private String cardNumber;
    private String cardType;
    private String currency;
    private String iban;
}
