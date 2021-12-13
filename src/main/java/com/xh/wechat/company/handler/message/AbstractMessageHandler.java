package com.xh.wechat.company.handler.message;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.message.WxCpMessageHandler;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-06 20:14
 */
@Slf4j
public abstract class AbstractMessageHandler implements WxCpMessageHandler {
    /**
     * 企业id
     */
    protected String corpId;
    /**
     * 应用id
     */
    protected Integer agentId;
}
