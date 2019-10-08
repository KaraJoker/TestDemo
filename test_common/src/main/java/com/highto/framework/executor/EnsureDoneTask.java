package com.highto.framework.executor;

/**
 * 确保任务完成的任务。
 * 
 * @author zheng chengdong
 *
 */
public interface EnsureDoneTask {

	/**
	 * 任务失败后调用此方法，获取下一次任务重试的延时时间。
	 * 
	 * @return >=0的数值代表下一次任务重试的延时时间（毫秒），-1代表任务完成，不再重试。
	 */
	public long getRetryDelay();

	public void run() throws Throwable;

}
