package com.example.sftp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.Link;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Link
public class Address {

    private String address;
    private String city;
    private String state;
    private String stateCode;
    private String postalCode;
    private Coordinates coordinates;
    private String country;
}
