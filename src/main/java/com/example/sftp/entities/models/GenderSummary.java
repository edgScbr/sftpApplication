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
public class GenderSummary {

    @Id
    @DataField(pos = 1)
    private String gender;
    @DataField(pos = 2)
    private Integer total;

}
