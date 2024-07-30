package com.example.sftp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.Link;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Link
public class Root {

    private User user;
}
