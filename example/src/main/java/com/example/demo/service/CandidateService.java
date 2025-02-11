package com.example.demo.service;

import com.example.demo.domain.Candidate;
import com.example.demo.domain.CandidateStatus;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    public String processCandidate(String name, CandidateStatus status) {
        Candidate candidate = new Candidate(name, status);
        return candidate.getStatus().process(candidate);
    }

}
