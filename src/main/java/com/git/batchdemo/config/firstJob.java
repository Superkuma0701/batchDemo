package com.git.batchdemo.config;

import com.git.batchdemo.listener.FirstJobListener;
import com.git.batchdemo.listener.FirstStepListener;
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

    private final FirstJobListener firstJobListener;

    private final FirstStepListener firstStepListener;




    @Autowired
    public firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager , FirstJobListener firstJobListener , FirstStepListener firstStepListener){
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.firstJobListener = firstJobListener;
        this.firstStepListener = firstStepListener;
    }

    @Bean
    public Job firstJobtest(){
        return new JobBuilder("job", jobRepository)
                .start(firstStep())
                .incrementer(new RunIdIncrementer())
                .listener(firstJobListener)
                .build();
    }

    @Bean
    public Step firstStep(){
        return new StepBuilder("step", jobRepository)
                .tasklet(firstTasklst(), transactionManager)
                .listener(firstStepListener)
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
