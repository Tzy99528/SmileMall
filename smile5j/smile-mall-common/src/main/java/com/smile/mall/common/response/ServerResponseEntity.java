package com.smile.mall.common.response;

import lombok.extern.slf4j.Slf4j;       // lombok 注解，自动生成 log 对象，用来打印日志
import lombok.Data;

import java.io.Serializable;        // 实现序列化接口，把内存中的 Java 对象 → 转换成字节流（二进制数据），便于传输到各种服务中
import java.util.Objects;

@Slf4j
@Data
public class ServerResponseEntity<T> implements Serializable
{

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 版本
     */
    private String version;

    /**
     * 时间
     */
    private Long timestamp;

    /**
     * 签名
     */
    private String sign;

    public ServerResponseEntity<T> setData(T data)
    {
        this.data = data;
        return this;
    }

    public boolean isSuccess()
    {
        return Objects.equals(ResponseEnum.OK.value(), this.code);
    }

    public boolean isFail()
    {
        return !Objects.equals(ResponseEnum.OK.value(), this.code);
    }

    public ServerResponseEntity()
    {
        this.version = "smile5j.v260601";
    }

    /**
     * 前端显示成功消息
     */
    public static <T> ServerResponseEntity<T> success(T data)
    {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.OK.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success()
    {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.OK.value());
        serverResponseEntity.setMsg(ResponseEnum.OK.getMsg());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success(Integer code, T data)
    {
        return success(String.valueOf(code), data);
    }

    public static <T> ServerResponseEntity<T> success(String code, T data)
    {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(code);
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    /**
     * 前端显示失败消息
     */
    public static <T> ServerResponseEntity<T> showFailMsg(String msg)
    {
        log.error(msg);
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setCode(ResponseEnum.SHOW_FAIL.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum)
    {
        log.error(responseEnum.toString());
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(responseEnum.getMsg());
        serverResponseEntity.setCode(responseEnum.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum, T data)
    {
        log.error(responseEnum.toString());
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(responseEnum.getMsg());
        serverResponseEntity.setCode(responseEnum.value());
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(String code, String msg, T data)
    {
        log.error(msg);
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setCode(code);
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(String code, String msg)
    {
        return fail(msg, code, null);
    }

    public static <T> ServerResponseEntity<T> fail(Integer code, T data)
    {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(String.valueOf(code));
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    @Override
    public String toString() 
    {
        return "ServerResponseEntity{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", version='" + version + '\'' +
                ", timestamp=" + timestamp +
                ", sign='" + sign + '\'' +
                '}';
    }
}
