package com.example.sftp.entities.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@CsvRecord( separator = "," )
public class CitySummary {

    @Id
    @Column(unique = true)
    @DataField(pos = 1)
    private String city;
    @DataField(pos = 2)
    private Long maleCount;
    @DataField(pos = 3)
    private Long femaleCount;
    @DataField(pos = 4)
    private Long otherCount;
}
