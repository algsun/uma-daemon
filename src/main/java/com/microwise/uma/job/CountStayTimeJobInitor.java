package com.microwise.uma.job;

import com.microwise.uma.service.impl.UserStayTimeServiceImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author xiedeng
 * @date 14-8-21
 */
public class CountStayTimeJobInitor {

    public static final String JOB_KEY = "countStayTimeJob";

    public static final String TRIGGER_KEY = "countStayTimeJobTrigger";

    Scheduler scheduler;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    public CountStayTimeJobInitor(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() throws SchedulerException {
        addJob(scheduler, JOB_KEY, ResetCheckFlagJob.class);
        addTrigger(scheduler, TRIGGER_KEY, JOB_KEY, "0 0 0 * * ?");
    }

    public void addJob(Scheduler scheduler, String triggerKey, Class<? extends Job> taskClass) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(taskClass)
                .storeDurably()
                .withIdentity(triggerKey)
                .build();
        scheduler.addJob(jobDetail, false);
    }

    public void addTrigger(Scheduler scheduler, String triggerKey, String jobKey, String rule) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("appContext", appContext);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(rule))
                .forJob(jobKey)
                .usingJobData(data)
                .build();
        scheduler.scheduleJob(trigger);
    }

    public static UserStayTimeServiceImpl getTaskService(JobExecutionContext jobExecutionContext) {
        JobDataMap data = jobExecutionContext.getTrigger().getJobDataMap();
        ApplicationContext appContext = (ApplicationContext) data.get("appContext");
        UserStayTimeServiceImpl userStayTimeService = (UserStayTimeServiceImpl) appContext.getBean(UserStayTimeServiceImpl.class);
        return userStayTimeService;
    }

    public static class ResetCheckFlagJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)
                throws JobExecutionException {
            getTaskService(jobExecutionContext).countUserStayTimeInZone(new Date());
        }
    }
}
