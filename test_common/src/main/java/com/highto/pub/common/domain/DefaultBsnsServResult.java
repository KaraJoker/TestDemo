package com.highto.pub.common.domain;

/**
 * 默认业务操作结果。不具备区分各种不同业务失败的能力，仅区分是系统异常，空指针，还是业务失败。
 * 
 * @author neo
 */
public class DefaultBsnsServResult extends BsnsServResult {

	private Object returnInfo;

	@Override
	protected String getDefaultFailMsg() {
		return null;
	}

	/**
	 * 普通不成功就是业务失败，这里不区分具体业务失败的原因。
	 * 
	 * @return
	 */
	public DefaultBsnsServResult unSuccessByBsnsFail() {
		unSuccess();
		return this;
	}

	public Object getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(Object returnInfo) {
		this.returnInfo = returnInfo;
	}

}