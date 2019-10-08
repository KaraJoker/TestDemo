package com.highto.framework.web;

import com.highto.framework.notify.Notifier;

public interface Response {
	/**
	 * 一个请求，一个reponse的flush。 被动通知 reponse。
	 *
	 * @param response
	 * @param <T>
	 */
	<T> void flush(T response);

	/**
	 * 主动通知 不应该放在此接口中。 而应该单独建立一个接口，来实现主动通知的职责。
	 *
	 * @return
	 * @throws UnsupportedOperationException
	 */
	Notifier createNotifier() throws UnsupportedOperationException;
}
