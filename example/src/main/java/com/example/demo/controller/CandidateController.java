package com.example.demo.controller;

import com.example.demo.constant.ConstantMessage;
import com.example.demo.domain.CandidateStatus;
import com.example.demo.dto.request.CandidateRequest;
import com.example.demo.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processCandidate(@RequestBody CandidateRequest request) {
        CandidateStatus candidateStatus;
        try {
            candidateStatus = CandidateStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ConstantMessage.BAD_REQUEST + request.getStatus());
        }
        String result = candidateService.processCandidate(request.getName(), candidateStatus);
        return ResponseEntity.ok(result);
    }

}
