package io.springbatch.springbatchlecture.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // 잡이 시작되기 전에 실행되는 메서드
        // 필요한 초기화 작업이나 로그를 여기에 작성할 수 있음
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // 잡이 완료된 후에 실행되는 메서드
        // 잡의 실행 시간을 계산하여 출력
        long time = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();
        System.out.println("총 소요시간 : " + time);
    }
    
}
