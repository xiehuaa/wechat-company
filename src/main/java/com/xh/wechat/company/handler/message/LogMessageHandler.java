package com.xh.wechat.company.handler.message;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;

import java.util.Map;

/**
 * 记录消息日志
 *
 * @author: XieHua
 * @date: 2021-12-06 20:15
 */
@Slf4j
public class LogMessageHandler extends AbstractMessageHandler {
    public LogMessageHandler(String corpId, Integer agentId) {
        this.corpId = corpId;
        this.agentId = agentId;
    }

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("企业：【{}】，应用：【{}】，接收到消息：【{}】", this.corpId, this.agentId, JSON.toJSON(wxMessage));
        return null;
    }
}
