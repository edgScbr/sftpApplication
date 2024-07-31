package com.example.sftp.controllers;

import com.example.sftp.controllers.responses.SummaryResponse;
import com.example.sftp.services.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summaries")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping
    public SummaryResponse getSummary() {
       return summaryService.getSummary();
    }
}
