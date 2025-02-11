package com.example.demo.domain;

import com.example.demo.constant.ConstantMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidateStatusTest {

    @Test
    @DisplayName("APPLIED 상태의 process 메서드 테스트")
    void testProcessApplied() {
        // given
        Candidate candidate = new Candidate("홍길동", CandidateStatus.APPLIED);

        // when
        String expected = ConstantMessage.APPLIED_MESSAGE + candidate.getName();
        String result = CandidateStatus.APPLIED.process(candidate);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("INTERVIEW 상태의 process 메서드 테스트")
    void testProcessInterview() {
        // given
        Candidate candidate = new Candidate("김철수", CandidateStatus.INTERVIEW);

        // when
        String expected = ConstantMessage.INTERVIEW_MESSAGE + candidate.getName();
        String result = CandidateStatus.INTERVIEW.process(candidate);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("OFFER 상태의 process 메서드 테스트")
    void testProcessOffer() {
        // given
        Candidate candidate = new Candidate("이영희", CandidateStatus.OFFER);

        // when
        String expected = ConstantMessage.OFFER_MESSAGE + candidate.getName();
        String result = CandidateStatus.OFFER.process(candidate);

        // then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("REJECT 상태의 process 메서드 테스트")
    void testProcessReject() {
        // given
        Candidate candidate = new Candidate("박민수", CandidateStatus.REJECT);

        // when
        String expected = ConstantMessage.REJECT_MESSAGE + candidate.getName();
        String result = CandidateStatus.REJECT.process(candidate);

        // then
        assertEquals(expected, result);
    }

}
