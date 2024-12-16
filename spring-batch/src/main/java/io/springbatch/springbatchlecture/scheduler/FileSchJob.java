package io.springbatch.springbatchlecture.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FileSchJob extends QuartzJobBean {

    @Autowired
    private Job fileJob; // 실행할 Spring Batch 잡 주입

    @Autowired
    private JobLauncher jobLauncher; // 잡 실행을 위한 JobLauncher 주입

    @Autowired
    private JobExplorer jobExplorer; // 잡 실행 정보를 탐색하기 위한 JobExplorer 주입

    @SneakyThrows // 예외를 선언하지 않고 던질 수 있게 함
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        // JobDataMap에서 'requestDate' 파라미터 추출
        String requestDate = (String) context.getJobDetail().getJobDataMap().get("requestDate");

        // 잡 파라미터 생성
        JobParameters jobParameters = new JobParametersBuilder()
                                    .addLong("id", new Date().getTime())
                                    .addString("requestDate", requestDate)
                                    .toJobParameters();

        // 기존에 실행된 잡 인스턴스 수 조회
        int jobInstanceCount = jobExplorer.getJobInstanceCount(fileJob.getName());
        List<JobInstance> jobInstances = jobExplorer.getJobInstances(fileJob.getName(), 0, jobInstanceCount);

        // 동일한 'requestDate'를 가진 잡 실행이 있는지 확인
        if(jobInstances.size() > 0) {
            for(JobInstance jobInstance : jobInstances){
                List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
                List<JobExecution> jobExecutionList = jobExecutions.stream().filter(jobExecution ->
                            jobExecution.getJobParameters().getString("requestDate").equals(requestDate))
                        .collect(Collectors.toList());
                if (jobExecutionList.size() > 0) {
                    throw new JobExecutionException(requestDate + " already exists"); // 중복 시 예외 발생
                }
            }
        }

        // 잡 실행
        jobLauncher.run(fileJob, jobParameters);
    }

}
