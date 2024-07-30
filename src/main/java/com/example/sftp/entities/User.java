package com.example.sftp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

@Data
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord( separator = "," )
public class User {
    @DataField(pos = 1)
    private int id;
    @DataField(pos = 2)
    private String firstName;
    @DataField(pos = 3)
    private String lastName;
    @DataField(pos = 4)
    private String maidenName;
    @DataField(pos = 5)
    private int age;
    @DataField(pos = 6)
    private String gender;
    @DataField(pos = 7)
    private String email;
    @DataField(pos = 8)
    private String phone;
    @DataField(pos = 9)
    private String username;
    @DataField(pos = 10)
    private String password;
    @DataField(pos = 11)
    private String birthDate;
    @DataField(pos = 12)
    private String image;
    @DataField(pos = 13)
    private String bloodGroup;
    @DataField(pos = 14)
    private double height;
    @DataField(pos = 15)
    private double weight;
    @DataField(pos = 16)
    private String eyeColor;
    @Link
    private Hair hair;
    @DataField(pos = 17)
    private String ip;
    @Link
    private Address address;
    @DataField(pos = 19)
    private String macAddress;
    @DataField(pos = 20)
    private String university;
    @Link
    private Bank bank;
    @Link
    private Company company;
    @DataField(pos = 23)
    private String ein;
    @DataField(pos = 24)
    private String ssn;
    @DataField(pos = 25)
    private String userAgent;
    @Link
    private Crypto crypto;
    @DataField(pos = 26)
    private String role;
}
