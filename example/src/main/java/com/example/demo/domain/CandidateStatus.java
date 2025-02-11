package com.example.demo.domain;

import com.example.demo.constant.ConstantMessage;

public enum CandidateStatus {
    APPLIED {
        @Override
        public String process(Candidate candidate) {
            String message = ConstantMessage.APPLIED_MESSAGE + candidate.getName();
            System.out.println(message);
            return message;
        }
    },
    INTERVIEW {
        @Override
        public String process(Candidate candidate) {
            String message = ConstantMessage.INTERVIEW_MESSAGE + candidate.getName();
            System.out.println(message);
            return message;
        }
    },
    OFFER {
        @Override
        public String process(Candidate candidate) {
            String message = ConstantMessage.OFFER_MESSAGE + candidate.getName();
            System.out.println(message);
            return message;
        }
    },
    REJECT {
        @Override
        public String process(Candidate candidate) {
            String message = ConstantMessage.REJECT_MESSAGE + candidate.getName();
            System.out.println(message);
            return message;
        }
    };

    public abstract String process(Candidate candidate);

}