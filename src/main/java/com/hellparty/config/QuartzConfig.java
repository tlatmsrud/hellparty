package com.hellparty.config;

import com.hellparty.quartz.SaveChattingJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

/**
 * title        : 스프링 Quartz 설정
 * author       : sim
 * date         : 2023-07-24
 * description  : 잡 스케줄러인 Quartz 설정
 */

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final Scheduler scheduler;

    private final JobListener jobListener;
    @PostConstruct
    public void init(){

        JobDetail saveChattingJobDetail = JobBuilder.newJob(SaveChattingJob.class)
                .withIdentity(JobKey.jobKey(SaveChattingJob.class.getSimpleName())).build();

        try {
            scheduler.scheduleJob(saveChattingJobDetail, buildJobTrigger("0 0 3 * * ?")); // 5초 마다 처리되도록함. 운영 시에는 새벽 3시 예정
            scheduler.getListenerManager().addJobListener(jobListener);
        }catch(SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }
}
