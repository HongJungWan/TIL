package io.springbatch.springbatchlecture.batch.job.api;

import io.springbatch.springbatchlecture.batch.listener.JobListener;
import io.springbatch.springbatchlecture.batch.tasklet.ApiEndTasklet;
import io.springbatch.springbatchlecture.batch.tasklet.ApiStartTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; // 잡 생성 팩토리 주입
    private final StepBuilderFactory stepBuilderFactory; // 스텝 생성 팩토리 주입
    private final ApiStartTasklet apiStartTasklet; // 시작 태스크릿 주입
    private final ApiEndTasklet apiEndTasklet; // 종료 태스크릿 주입
    private final Step jobStep; // 잡 스텝 주입

    @Bean
    public Job apiJob() throws Exception {
        return jobBuilderFactory.get("apiJob") // 잡 이름 설정
                .incrementer(new RunIdIncrementer()) // 잡 실행 시 고유 ID 부여
                .listener(new JobListener()) // 잡 리스너 등록
                .start(apiStep1()) // 첫 번째 스텝 시작
                .next(jobStep) // 다음 스텝으로 이동
                .next(apiStep2()) // 마지막 스텝 실행
                .build();
    }

    @Bean
    public Step apiStep1() throws Exception {
        return stepBuilderFactory.get("apiStep") // 스텝 이름 설정
                .tasklet(apiStartTasklet) // 시작 태스크릿 설정
                .build();
    }

    @Bean
    public Step apiStep2() throws Exception {
        return stepBuilderFactory.get("apiStep2") // 스텝 이름 설정
                .tasklet(apiEndTasklet) // 종료 태스크릿 설정
                .build();
    }

}
