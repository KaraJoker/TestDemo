package com.highto.framework.ddd;

public class NullEntityException extends RuntimeException {

	private static final long serialVersionUID = -4544883160481871185L;

	public NullEntityException(String msg) {
		super(msg);
	}

}
