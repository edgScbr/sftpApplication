package com.example.sftp.controllers.responses;

import com.example.sftp.entities.models.AgeRangeSummary;
import com.example.sftp.entities.models.CitySummary;
import com.example.sftp.entities.models.GenderSummary;
import com.example.sftp.entities.models.OperatingSystemSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SummaryResponse {

    private List<GenderSummary> genderSummaryList;
    private List<AgeRangeSummary> ageRangeSummaryList;
    private List<CitySummary> citySummaryList;
    private List<OperatingSystemSummary> operatingSystemSummaryList;
}
