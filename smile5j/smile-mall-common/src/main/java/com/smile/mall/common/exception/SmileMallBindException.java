package com.smile.mall.common.exception;

import com.smile.mall.common.response.ResponseEnum;
import com.smile.mall.common.response.ServerResponseEntity;
import lombok.Getter;

@Getter
public class SmileMallBindException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = -4137688758944857209L;

	/**
	 * http状态码
	 */
	private String code;

	private Object object;

	private ServerResponseEntity<?> serverResponseEntity;

	public SmileMallBindException(ResponseEnum responseEnum) {
		super(responseEnum.getMsg());
		this.code = responseEnum.value();
	}
	/**
	 * @param responseEnum
	 */
	public SmileMallBindException(ResponseEnum responseEnum, String msg) {
		super(msg);
		this.code = responseEnum.value();
	}

	public SmileMallBindException(ServerResponseEntity<?> serverResponseEntity) {
		this.serverResponseEntity = serverResponseEntity;
	}


	public SmileMallBindException(String msg) {
		super(msg);
		this.code = ResponseEnum.SHOW_FAIL.value();
	}

	public SmileMallBindException(String msg, Object object) {
		super(msg);
		this.code = ResponseEnum.SHOW_FAIL.value();
		this.object = object;
	}

}