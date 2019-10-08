package com.highto.framework.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 超时线程池。每个线程执行超时后被强制终止。
 * 
 * @author zheng chengdong
 *
 */
public class ExpiredTaskExecutor implements Executor {

	private ExecutorService executorService;

	private int expiredTime;

	private ExpiredTaskFilter expiredTaskFilter;

	/**
	 * @param executorService
	 * @param expiredTime
	 *            单位：秒
	 */
	public ExpiredTaskExecutor(ExecutorService executorService, int expiredTime) {
		this.executorService = executorService;
		this.expiredTime = expiredTime;
	}

	/**
	 * @param executorService
	 * @param expiredTime
	 *            单位：秒
	 * @param expiredTaskFilter
	 */
	public ExpiredTaskExecutor(ExecutorService executorService, int expiredTime, ExpiredTaskFilter expiredTaskFilter) {
		this(executorService, expiredTime);
		this.expiredTaskFilter = expiredTaskFilter;
	}

	@Override
	public void execute(Runnable task) {
		if (expiredTaskFilter != null && expiredTaskFilter.isExpiredTask(task)) {
			Future f = executorService.submit(task);
			executorService.execute(() -> {
				try {
					f.get(expiredTime, TimeUnit.SECONDS);
				} catch (Exception e) {
					f.cancel(true);
				}
			});
		} else {
			executorService.execute(task);
		}
	}
}
