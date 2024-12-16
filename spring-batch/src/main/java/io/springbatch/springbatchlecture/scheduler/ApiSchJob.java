package io.springbatch.springbatchlecture.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
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
public class ApiSchJob extends QuartzJobBean{

	@Autowired
	private Job apiJob; // 실행할 Spring Batch 잡 주입

	@Autowired
	private JobLauncher jobLauncher; // 잡 실행을 위한 JobLauncher 주입

	@SneakyThrows // 예외를 선언하지 않고 던질 수 있게 함
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		// 현재 시간을 기반으로 유니크한 잡 파라미터 생성
		JobParameters jobParameters = new JobParametersBuilder()
									.addLong("id", new Date().getTime())
									.toJobParameters();
		
		// Spring Batch 잡 실행
		jobLauncher.run(apiJob, jobParameters);
	}
	
}
