package com.highto.framework.executor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 确保任务完成的线程池。每个任务如果执行出现异常，那就要塞回到线程池重新执行。<br/>
 * 任务可自行决定何时重新执行或者任务完成。
 * 
 * @author zheng chengdong
 *
 */
public class EnsureDoneTaskExecutor {

	private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

	public EnsureDoneTaskExecutor(int corePoolSize) {
		scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
	}

	public void execute(EnsureDoneTask eTask) {
		scheduledThreadPoolExecutor.execute(new Task(eTask));
	}

	public void execute(EnsureDoneTask eTask, Runnable callback) {
		scheduledThreadPoolExecutor.execute(new Task(eTask));
	}

	/**
	 * 用于异步任务
	 * 
	 * @param task
	 */
	public void retry(EnsureDoneTask eTask) {
		long nanoDelay = eTask.getRetryDelay();
		if (nanoDelay != -1) {
			scheduledThreadPoolExecutor.schedule(new Task(eTask), nanoDelay, TimeUnit.NANOSECONDS);
		}
	}

	private class Task implements Runnable {
		private EnsureDoneTask eTask;

		private Task(EnsureDoneTask eTask) {
			this.eTask = eTask;
		}

		@Override
		public void run() {
			try {
				eTask.run();
			} catch (Throwable e) {
				retry(eTask);
			}
		}
	}

}
