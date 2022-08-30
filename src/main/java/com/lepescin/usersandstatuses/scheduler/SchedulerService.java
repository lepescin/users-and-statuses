package com.lepescin.usersandstatuses.scheduler;

import com.lepescin.usersandstatuses.model.User;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class SchedulerService {

    public static final int MINUTES_TO_USER_AWAY = 5;

    public void prepareScheduledSetUserStatusAwayJob(User user) {
        LocalDateTime triggerAt = user.getStatusChanged().plusMinutes(MINUTES_TO_USER_AWAY);

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        JobDetail job = JobBuilder
                .newJob(SetUserStatusAwayJob.class)
                .withIdentity("scheduled-dma-" + user.getId(), "user")
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger-scheduled-dma-" + user.getId(), "user")
                .startAt(Timestamp.valueOf(triggerAt)).build();

        job.getJobDataMap().put("userId", user.getId());
        job.getJobDataMap().put("scheduler", schedulerFactory);

        try {
            deleteExistingJob(user);
            schedulerFactory.getScheduler().scheduleJob(job, trigger);
            schedulerFactory.getScheduler().start();
            log.info("In prepareScheduledSetUserStatusAwayJob - user with id : {}; status changed date : {}; trigger time: {}", user.getId(), user.getStatusChanged(), triggerAt);
        } catch (SchedulerException e) {
            log.error("Error while prepareScheduledSetUserStatusAwayJob", e);
        }
    }

    public void deleteExistingJob(User user) {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    if (jobName.equals("scheduled-dma-" + user.getId()) && jobGroup.equals("user")) {
                        scheduler.deleteJob(jobKey);
                        log.info("In deleteExistingJob - job with jobKeyName : {} and jobKeyGroup : {} deleted while updating status online", jobName, jobGroup);
                    }
                }
            }
        } catch (SchedulerException e) {
            log.error("Error while getting scheduler", e);
        }
    }
}
