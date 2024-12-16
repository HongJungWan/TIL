package io.springbatch.springbatchlecture.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ApiJobRunner extends JobRunner {

    @Autowired
    private Scheduler scheduler; // Quartz 스케줄러 인스턴스 주입

    @Override
    protected void doRun(ApplicationArguments args) {

    	 // JobDetail 생성: ApiSchJob 클래스로 정의된 잡을 'apiJob' 이름과 'batch' 그룹으로 생성
        JobDetail jobDetail = buildJobDetail(ApiSchJob.class, "apiJob", "batch", new HashMap());
        
        // Trigger 생성: 크론 표현식을 사용하여 30초마다 실행되도록 설정
        Trigger trigger = buildJobTrigger("0/30 * * * * ?");

        try {
        	// 잡과 트리거를 스케줄러에 등록하여 실행 예약
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace(); // 스케줄링 오류 시 예외 처리
        }
    }

}
