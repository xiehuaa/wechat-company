package com.xh.wechat.company.handler;

import com.xh.wechat.company.domain.result.R;
import com.xh.wechat.company.exception.WechatCompanyErrorCode;
import com.xh.wechat.company.exception.WechatCompanyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 17:05
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WechatCompanyException.class)
    public R<Object> handlerMallException(WechatCompanyException wechatCompanyException) {

        return R.fail(wechatCompanyException.getCode(), wechatCompanyException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<Object> handlerException(Exception exception) {
        log.error("处理Exception异常：", exception);
        return R.fail(WechatCompanyErrorCode.UNKNOWN_ERROR);
    }
}
