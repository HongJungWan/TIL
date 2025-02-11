package com.example.demo.domain;

public class Candidate {
    private String name;
    private CandidateStatus status;

    public Candidate(String name, CandidateStatus status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public CandidateStatus getStatus() {
        return status;
    }

    public void setStatus(CandidateStatus status) {
        this.status = status;
    }

}
