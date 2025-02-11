package com.example.demo.service;

import com.example.demo.constant.ConstantMessage;
import com.example.demo.domain.Candidate;
import com.example.demo.domain.CandidateStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateServiceTest {

    private final CandidateService candidateService = new CandidateService();

    @Test
    @DisplayName("APPLIED 상태일 때 후보자 처리 테스트")
    void testProcessCandidateWithAppliedStatus() {
        // given
        String name = "홍길동";
        CandidateStatus status = CandidateStatus.APPLIED;
        String expected = ConstantMessage.APPLIED_MESSAGE + name;

        // when
        String result = candidateService.processCandidate(name, status);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("INTERVIEW 상태일 때 후보자 처리 테스트")
    void testProcessCandidateWithInterviewStatus() {
        // given
        String name = "김철수";
        CandidateStatus status = CandidateStatus.INTERVIEW;
        String expected = ConstantMessage.INTERVIEW_MESSAGE + name;

        // when
        String result = candidateService.processCandidate(name, status);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("OFFER 상태일 때 후보자 처리 테스트")
    void testProcessCandidateWithOfferStatus() {
        // given
        String name = "이영희";
        CandidateStatus status = CandidateStatus.OFFER;
        String expected = ConstantMessage.OFFER_MESSAGE + name;

        // when
        String result = candidateService.processCandidate(name, status);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("REJECT 상태일 때 후보자 처리 테스트")
    void testProcessCandidateWithRejectStatus() {
        // given
        String name = "박민수";
        CandidateStatus status = CandidateStatus.REJECT;
        String expected = ConstantMessage.REJECT_MESSAGE + name;

        // when
        String result = candidateService.processCandidate(name, status);

        // then
        assertEquals(expected, result);
    }
}