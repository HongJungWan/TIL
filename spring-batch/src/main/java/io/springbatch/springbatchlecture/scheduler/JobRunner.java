package io.springbatch.springbatchlecture.scheduler;

import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Map;

import static org.quartz.JobBuilder.newJob;

public abstract class JobRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        doRun(args); // 추상 메서드 호출
    }

    // 서브클래스에서 구현해야 하는 추상 메서드
    protected abstract void doRun(ApplicationArguments args);

    /**
     * 잡 트리거를 빌드하는 메서드.
     *
     * @param scheduleExp 크론 표현식
     * @return 생성된 Trigger 객체
     */
    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)) // 크론 스케줄 설정
                .build();
    }

    /**
     * 잡 상세 정보를 빌드하는 메서드.
     *
     * @param job 클래스 타입
     * @param name 잡 이름
     * @param group 잡 그룹
     * @param params 잡에 전달할 파라미터 맵
     * @return 생성된 JobDetail 객체
     */
    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params); // 파라미터 맵을 JobDataMap에 추가

        return newJob(job).withIdentity(name, group) // 잡의 이름과 그룹 설정
                .usingJobData(jobDataMap) // 잡 데이터 맵 설정
                .build();
    }
    
}
