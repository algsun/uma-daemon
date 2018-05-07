package com.microwise.uma.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库写操作统计任务初始化
 * 
 * @author xu.baoji
 * @date 2013.09.27
 */
public class StatsJobInitor {
	// 计算周期, 每 10 秒计算一次
	public static final int INTERNAL = 10;

	public static final String JOB_KEY = "stats.speedJob";
	public static final String TRIGGER_KEY = "stats.speedTrigger";

	private Scheduler scheduler;

	public StatsJobInitor(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@SuppressWarnings("unused")
	private void initQuartz() throws SchedulerException {
		JobDetail job = JobBuilder.newJob(StatsWriteSpeedJob.class).storeDurably()
				.withIdentity(JOB_KEY).build();
		scheduler.addJob(job, false);

		Trigger simpleTrigger = TriggerBuilder
				.newTrigger()
				.withIdentity(TRIGGER_KEY)
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(INTERNAL)
								.repeatForever()).forJob(JOB_KEY).build();

		scheduler.scheduleJob(simpleTrigger);

	}

	public static class StatsWriteSpeedJob implements Job {
		public static final Logger log = LoggerFactory.getLogger(StatsWriteSpeedJob.class);

		@SuppressWarnings("unchecked")
		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			PersistenceState stats = PersistenceState.getInst();
			AtomicInteger writes = stats.getPacketWritesForSpeed();
			int writeTimes = writes.getAndSet(0);
			float speedPerSecond2 = BigDecimal.valueOf((double) writeTimes / INTERNAL)
					.setScale(1, RoundingMode.HALF_UP).floatValue();
			stats.getPacketWritesPerSecond().set(Float.valueOf(speedPerSecond2));
			log.debug("write: {}, speed: {}", writeTimes, speedPerSecond2);
		}
	}
}
