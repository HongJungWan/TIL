package io.springbatch.springbatchlecture.scheduler;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.quartz.JobBuilder.newJob;

@Component
public class FileJobRunner extends JobRunner {

    @Autowired
    private Scheduler scheduler; // Quartz 스케줄러 인스턴스 주입

    @Override
    protected void doRun(ApplicationArguments args) {

        String[] sourceArgs = args.getSourceArgs(); // 애플리케이션 실행 시 전달된 인자들
        // JobDetail 생성: FileSchJob 클래스로 정의된 잡을 'fileJob' 이름과 'batch' 그룹으로 생성
        JobDetail jobDetail = buildJobDetail(FileSchJob.class, "fileJob", "batch", new HashMap());

        // Trigger 생성: 크론 표현식을 사용하여 50초마다 실행되도록 설정
        Trigger trigger = buildJobTrigger("0/50 * * * * ?");

        // JobDataMap에 'requestDate' 파라미터 추가
        jobDetail.getJobDataMap().put("requestDate", sourceArgs[0]);

        try {
            // 잡과 트리거를 스케줄러에 등록하여 실행 예약
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace(); // 스케줄링 오류 시 예외 처리
        }
    }

}
