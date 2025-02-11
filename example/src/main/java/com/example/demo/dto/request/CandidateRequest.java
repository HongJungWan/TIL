package com.example.demo.dto.request;

public class CandidateRequest {
    private String name;
    private String status;

    public CandidateRequest() {}

    public CandidateRequest(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

}
