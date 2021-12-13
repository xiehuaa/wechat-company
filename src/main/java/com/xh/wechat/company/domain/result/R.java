package com.xh.wechat.company.domain.result;

import com.xh.wechat.company.exception.WechatCompanyErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 16:58
 */
@Setter
@Getter
public class R<T> implements Serializable {
    private static final int SUCCESS_CODE = 0;
    private static final String SUCCESS_MSG = "请求成功";

    private Integer code;
    private String msg;
    private T data;

    /**
     * 获取成功结果对象
     *
     * @param data 数据
     * @param <T>  泛型
     * @return R
     */
    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(SUCCESS_CODE);
        r.setMsg(SUCCESS_MSG);
        r.setData(data);

        return r;
    }

    /**
     * 获取失败对象
     *
     * @param code 错误码
     * @param msg  错误消息
     * @param <T>  泛型
     * @return R
     */
    public static <T> R<T> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 获取失败对象
     *
     * @param errorCode MallErrorCode对象
     * @param <T>       泛型
     * @return R
     */
    public static <T> R<T> fail(WechatCompanyErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 获取失败对象
     *
     * @param errorCode MallErrorCode对象
     * @param data      数据
     * @param <T>       泛型
     * @return R
     */
    public static <T> R<T> fail(WechatCompanyErrorCode errorCode, T data) {
        return fail(errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 获取失败对象
     *
     * @param code 错误码
     * @param msg  错误消息
     * @param data 数据
     * @param <T>  泛型
     * @return R
     */
    public static <T> R<T> fail(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);

        return r;
    }
}
