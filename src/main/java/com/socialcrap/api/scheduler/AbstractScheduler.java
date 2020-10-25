package com.socialcrap.api.scheduler;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

public abstract class AbstractScheduler {

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	@PostConstruct
	public void init() {
		invokeScheduler(getInitialDelay(), getNextDelay(), getTimeUnit());
	}

	public void invokeScheduler(int initialDelay, int nextDelay, TimeUnit timeUnit) {
		Long initialTime = System.currentTimeMillis() + (initialDelay * 1000);
		System.out.println("Scheduler invoked with activated " + activated() + " Scheduler will First run at "
				+ new Date(initialTime) + " with next delay of " + nextDelay);
		executor.scheduleAtFixedRate(runnable, initialDelay, nextDelay, timeUnit);
	}

	public void invokeOnce() {
		executor.submit(runnable);
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (activated()) {
				try {
					runScheduler();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	public abstract void runScheduler();

	public abstract int getInitialDelay();

	public abstract int getNextDelay();

	public abstract boolean activated();

	public abstract TimeUnit getTimeUnit();

}
