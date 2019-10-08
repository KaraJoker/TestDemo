package com.highto.pub.common.web.viewobj;

import com.highto.pub.common.domain.BsnsServResult;
import com.highto.pub.common.domain.DefaultBsnsServResult;

/**
 * BsnsServResult ajax返回的默认VO
 * 
 * @author zheng chengdong
 *
 */
public class BsnsServResultForJsonVO {

	private boolean success;

	private String errMsg;

	private Object returnInfo;

	public BsnsServResultForJsonVO() {
	}

	public BsnsServResultForJsonVO(BsnsServResult result) {
		success = result.isSuccess();
		if (!success) {
			errMsg = result.getDefaultErrorMsg();
		}
	}

	public BsnsServResultForJsonVO(boolean success, String errMsg) {
		this.success = success;
		this.errMsg = errMsg;
	}

	public BsnsServResultForJsonVO(DefaultBsnsServResult defaultBsnsServResult, String failMsg) {
		this.success = defaultBsnsServResult.isSuccess();
		if (!success) {
			String defaultErrorMsg = defaultBsnsServResult.getDefaultErrorMsg();
			if (defaultErrorMsg == null) {
				errMsg = failMsg;
			} else {
				errMsg = defaultErrorMsg;
			}
		} else {
			returnInfo = defaultBsnsServResult.getReturnInfo();
		}
	}

	public static BsnsServResultForJsonVO success() {
		BsnsServResultForJsonVO sv = new BsnsServResultForJsonVO();
		sv.setSuccess(true);
		return sv;
	}

	public static BsnsServResultForJsonVO unSuccess(String errMsg) {
		BsnsServResultForJsonVO sv = new BsnsServResultForJsonVO();
		sv.setSuccess(false);
		sv.setErrMsg(errMsg);
		return sv;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(Object returnInfo) {
		this.returnInfo = returnInfo;
	}

}
