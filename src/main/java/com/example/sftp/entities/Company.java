package com.example.sftp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.Link;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Link
public class Company {

    private String department;
    private String name;
    private String title;
    private Address address;
}
