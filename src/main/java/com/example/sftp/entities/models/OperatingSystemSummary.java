package com.example.sftp.entities.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord( separator = "," )
public class OperatingSystemSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DataField(pos = 1)
    private Long id;
    @DataField(pos = 2)
    private String os;
    @DataField(pos = 3)
    private Long total;
}
