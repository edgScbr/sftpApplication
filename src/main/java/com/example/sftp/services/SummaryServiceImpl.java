package com.example.sftp.services;


import com.example.sftp.repositories.AgeRangeRepository;
import com.example.sftp.repositories.CityRepository;
import com.example.sftp.repositories.GenderRepository;
import com.example.sftp.repositories.OperatingSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.sftp.controllers.responses.SummaryResponse;


@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService{

    private final AgeRangeRepository ageRangeRepository;
    private final CityRepository cityRepository;
    private final GenderRepository genderRepository;
    private final OperatingSystemRepository operatingSystemRepository;


    @Override
    public SummaryResponse getSummary() {
        SummaryResponse summaryResponse = new SummaryResponse();
        summaryResponse.setGenderSummaryList(genderRepository.findAll());
        summaryResponse.setAgeRangeSummaryList(ageRangeRepository.findAll());
        summaryResponse.setCitySummaryList(cityRepository.findAll());
        summaryResponse.setOperatingSystemSummaryList(operatingSystemRepository.findAll());
        return summaryResponse;
    }
}
