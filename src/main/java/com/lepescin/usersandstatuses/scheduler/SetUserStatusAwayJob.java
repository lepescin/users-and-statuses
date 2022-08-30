package com.lepescin.usersandstatuses.scheduler;

import com.lepescin.usersandstatuses.context.SpringContext;
import com.lepescin.usersandstatuses.model.Status;
import com.lepescin.usersandstatuses.model.User;
import com.lepescin.usersandstatuses.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;

@Slf4j
public class SetUserStatusAwayJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        UserService userService = SpringContext.getApplicationContext().getBean(UserService.class);
        User user = userService.getUserById(dataMap.getInt("userId"));
        userService.updateUserStatus(user, Status.AWAY);

        log.info("In execute - setting user status away is started for user with id: {}; current time is: {}", dataMap.get("userId"), LocalDateTime.now());
    }


}
