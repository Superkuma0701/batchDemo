package com.git.batchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class firstJob {


    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;




    @Autowired
    public firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager ){
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;

    }

    @Bean
    public Job firstJobtest(){
        return new JobBuilder("job", jobRepository)
                .start(firstStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step firstStep(){
        return new StepBuilder("step", jobRepository)
                .tasklet(firstTasklst(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet firstTasklst(){
        return (contribution, chunkContext) -> {
            System.out.println("Hello, Spring Batch!");
            return RepeatStatus.FINISHED;
        };
    }

}
