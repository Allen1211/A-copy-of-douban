package com.allen.douban.entity;

public class Msg{
	private boolean isSuccess;
	private String message;
	private Object data;
	
	public Msg() {
		// TODO Auto-generated constructor stub
	}

	
	public Msg(boolean isSuccess, String message, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = data;
	}

	public Msg(boolean isSuccess, String message) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = null;
	}

	
	public Msg(String message, Object data) {
		super();
		this.isSuccess = true;
		this.message = message;
		this.data = data;
	}

	public Msg(boolean isSuccess, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.data = data;
	}


	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	

	
	
}
