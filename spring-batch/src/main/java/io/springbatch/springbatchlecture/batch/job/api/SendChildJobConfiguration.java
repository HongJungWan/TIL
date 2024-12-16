package io.springbatch.springbatchlecture.batch.job.api;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendChildJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; // 잡 생성 팩토리 주입
    private final StepBuilderFactory stepBuilderFactory; // 스텝 생성 팩토리 주입
    private final Step apiMasterStep; // 마스터 스텝 주입
    private final JobLauncher jobLauncher; // 잡 실행기 주입

    @Bean
    public Step jobStep() throws Exception {
        return stepBuilderFactory.get("jobStep") // 스텝 이름 설정
                .job(childJob()) // 자식 잡 설정
                .launcher(jobLauncher) // 잡 런처 설정
                .build();
    }

    @Bean
    public Job childJob() throws Exception {
        return jobBuilderFactory.get("childJob") // 자식 잡 이름 설정
                .start(apiMasterStep) // 마스터 스텝 시작
                .build();
    }

}
