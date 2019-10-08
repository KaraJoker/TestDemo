package com.highto.pub.common.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 一般的业务服务结果
 * 
 * @author zheng chengdong
 *
 */
public abstract class BsnsServResult {

	/**
	 * 是否成功.默认为成功
	 */
	protected boolean success = true;

	/**
	 * 系统错误
	 */
	protected Throwable sysError;

	/**
	 * 业务异常
	 */
	protected Exception bsnsException;

	/**
	 * 空实体异常记录。 key代表实体类型，value代表实体id
	 */
	protected Map<String, String> nullEntityMap;

	/**
	 * 设为不成功
	 */
	public void unSuccess() {
		success = false;
	}

	/**
	 * 由于系统错误设为不成功
	 * 
	 * @param sysErr
	 */
	public <T extends BsnsServResult> T unSuccessBySysErr(Throwable sysErr) {
		unSuccess();
		sysError = sysErr;
		return (T) this;
	}

	/**
	 * 由于业务异常设为不成功
	 * 
	 * @param bE
	 */
	public <T extends BsnsServResult> T unSuccessByBsnsException(Exception bE) {
		unSuccess();
		bsnsException = bE;
		return (T) this;
	}

	/**
	 * 由于实体为空设为不成功
	 * 
	 * @param class
	 *            实体类型，可以是任意字符串。
	 * @param entityId
	 *            实体id。
	 * @return
	 */
	public <T extends BsnsServResult> T unSuccessByNullEntity(Class<?> clazz, String entityId) {
		unSuccess();
		if (nullEntityMap == null) {
			nullEntityMap = new HashMap<String, String>();
		}
		nullEntityMap.put(clazz.getClass().toString(), entityId);
		return (T) this;
	}

	/**
	 * 由于实体为空设为不成功
	 * 
	 * @param clazz
	 *            实体Class对象
	 * @return
	 */
	public <T extends BsnsServResult> T unSuccessByNullEntity(Class<?> clazz) {
		unSuccess();
		if (nullEntityMap == null) {
			nullEntityMap = new HashMap<String, String>();
		}
		nullEntityMap.put(clazz.getClass().toString(), "");
		return (T) this;
	}

	/**
	 * 任何一个实体为空设为不成功
	 * 
	 */
	public <T extends BsnsServResult> T unSuccessAnyEntityNull(Object... entities) {
		for (Object entity : entities) {
			if (entity == null) {
				unSuccess();
			}
		}
		return (T) this;
	}

	/**
	 * 可用于显示的默认出错信息。<br/>
	 * 对于高级要求的出错信息显示，请用VO包装此对象，自行处理。
	 * 
	 * @return
	 */
	public String getDefaultErrorMsg() {
		if (sysError != null) {
			return sysError.getMessage();
		} else if (bsnsException != null) {
			return bsnsException.getMessage();
		} else if (nullEntityMap != null) {
			return getNullEntityMessage();
		} else {
			return getDefaultFailMsg();
		}
	}

	private String getNullEntityMessage() {
		String message = "null entity message start:";
		for (String key : nullEntityMap.keySet()) {
			message = message + "\n";
			message = message + key + " ";
			message = message + nullEntityMap.get(key);
		}
		message = message + "\n" + "null entity message over!\n";
		return message;
	}

	/**
	 * 具体业务可用于显示的默认出错信息。<br/>
	 * 对于高级要求的出错信息显示，请用VO包装此对象，自行处理。
	 * 
	 * @return
	 */
	protected abstract String getDefaultFailMsg();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Throwable getSysError() {
		return sysError;
	}

	public void setSysError(Throwable sysError) {
		this.sysError = sysError;
	}

	public Exception getBsnsException() {
		return bsnsException;
	}

	public void setBsnsException(Exception bsnsException) {
		this.bsnsException = bsnsException;
	}

}
