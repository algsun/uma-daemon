package com.microwise.uma.job;

import com.microwise.uma.service.impl.ResetCheckFlagServiceImpl;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @author xiedeng
 * @date 14-8-21
 */
public class ResetCheckFlagJobInitor {

    public static final int INTERVAL = 7200;

    public static final String JOB_KEY = "resetCheckFlagJob";

    public static final String TRIGGER_KEY = "resetCheckFlagJobTrigger";

    Scheduler scheduler;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    public ResetCheckFlagJobInitor(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() throws SchedulerException {
        addJob(scheduler, JOB_KEY, ResetCheckFlagJob.class);
        addTrigger(scheduler, TRIGGER_KEY, JOB_KEY, INTERVAL);
    }

    public void addJob(Scheduler scheduler, String triggerKey, Class<? extends Job> taskClass) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(taskClass)
                .storeDurably()
                .withIdentity(triggerKey)
                .build();
        scheduler.addJob(jobDetail, false);
    }

    public void addTrigger(Scheduler scheduler, String triggerKey, String jobKey, int interval) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("appContext", appContext);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(interval).repeatForever())
                .forJob(jobKey)
                .usingJobData(data)
                .build();
        scheduler.scheduleJob(trigger);
    }

    public static ResetCheckFlagServiceImpl getTaskService(JobExecutionContext jobExecutionContext) {
        JobDataMap data = jobExecutionContext.getTrigger().getJobDataMap();
        ApplicationContext appContext = (ApplicationContext) data.get("appContext");
        ResetCheckFlagServiceImpl resetCheckFlagService = (ResetCheckFlagServiceImpl) appContext.getBean(ResetCheckFlagServiceImpl.class);
        return resetCheckFlagService;
    }

    public static class ResetCheckFlagJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)
                throws JobExecutionException {
            getTaskService(jobExecutionContext).resetCheckFlag();
        }
    }
}
