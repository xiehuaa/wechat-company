package com.xh.wechat.company.handler.message;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;

import java.util.Map;

/**
 * 处理外部联系人变更事件
 *
 * @author: XieHua
 * @date: 2021-12-06 20:47
 */
public class ExternalContactChangeMessageHandler extends AbstractMessageHandler {
    public ExternalContactChangeMessageHandler(String corpId, Integer agentId) {
        this.corpId = corpId;
        this.agentId = agentId;
    }

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context, WxCpService wxCpService, WxSessionManager sessionManager) throws WxErrorException {
        return null;
    }
}
