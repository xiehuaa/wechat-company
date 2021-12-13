package com.xh.wechat.company.config;

import com.xh.wechat.company.handler.message.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-06 20:04
 */
@Configuration
@EnableConfigurationProperties({WechatCompanyProperties.class})
public class WechatCompanyConfig {
    private static final Map<Integer, WxCpService> WX_CP_SERVICE_MAP = new HashMap<>();
    private static final Map<Integer, WxCpMessageRouter> MESSAGE_ROUTER_MAP = new HashMap<>();

    private final WechatCompanyProperties wechatCompanyProperties;

    @Autowired
    public WechatCompanyConfig(WechatCompanyProperties wechatCompanyProperties) {
        this.wechatCompanyProperties = wechatCompanyProperties;
    }

    @PostConstruct
    public void initServices() {
        WechatCompanyProperties.AppProperties appProperties = wechatCompanyProperties.getApp();

        WxCpDefaultConfigImpl configStorage = new WxCpDefaultConfigImpl();
        configStorage.setCorpId(wechatCompanyProperties.getCorpId());
        configStorage.setCorpSecret(appProperties.getSecret());
        configStorage.setAgentId(appProperties.getAgentId());
        configStorage.setToken(appProperties.getToken());
        configStorage.setAesKey(appProperties.getAesKey());

        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(configStorage);

        WX_CP_SERVICE_MAP.put(appProperties.getAgentId(), wxCpService);

        WxCpMessageRouter messageRouter = this.newRouter(wechatCompanyProperties.getCorpId(), appProperties.getAgentId(), wxCpService);

        MESSAGE_ROUTER_MAP.put(appProperties.getAgentId(), messageRouter);
    }

    /**
     * 通过agentId获取对应的wxCpService
     *
     * @param agentId agentId
     * @return wxCpService对象
     */
    public WxCpService getWxCpService(Integer agentId) {
        if (WX_CP_SERVICE_MAP.isEmpty()) {
            return null;
        }
        return WX_CP_SERVICE_MAP.get(agentId);
    }

    /**
     * 通过agentId获取消息路由对象
     *
     * @param agentId agnetId
     * @return 消息路由对象
     */
    public WxCpMessageRouter getMessageRouter(Integer agentId) {
        return MESSAGE_ROUTER_MAP.get(agentId);
    }

    /**
     * 创建消息路由
     *
     * @param wxCpService 企业微信服务
     * @return 消息路由
     */
    private WxCpMessageRouter newRouter(String corpId, Integer agentId, WxCpService wxCpService) {
        WxCpMessageRouter messageRouter = new WxCpMessageRouter(wxCpService);

        LogMessageHandler logMessageHandler = new LogMessageHandler(corpId, agentId);
        ExternalContactChangeMessageHandler externalContactChangeMessageHandler = new ExternalContactChangeMessageHandler(corpId, agentId);
        ContactChangeMessageHandler contactChangeMessageHandler = new ContactChangeMessageHandler(corpId, agentId);
        EnterAgentMessageHandler enterAgentMessageHandler = new EnterAgentMessageHandler(corpId, agentId);

        SubscribeMessageHandler subscribeMessageHandler = new SubscribeMessageHandler(corpId, agentId);
        UnsubscribeMessageHandler unsubscribeMessageHandler = new UnsubscribeMessageHandler(corpId, agentId);

        NormalMessageHandler normalMessageHandler = new NormalMessageHandler(corpId, agentId);

        // 记录日志
        messageRouter.rule().handler(logMessageHandler).next();

        // 外部联系人变更事件
        messageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.CHANGE_EXTERNAL_CONTACT).handler(externalContactChangeMessageHandler)
                .end();
        // 通讯录变更事件
        messageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.CHANGE_CONTACT).handler(contactChangeMessageHandler).end();
        // 进入应用事件
        messageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.ENTER_AGENT).handler(enterAgentMessageHandler).end();

        // 关注应用事件
        messageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.SUBSCRIBE).handler(subscribeMessageHandler).end();

        // 取消关注应用事件
        messageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.UNSUBSCRIBE).handler(unsubscribeMessageHandler).end();

        // 默认
        messageRouter.rule().async(false).handler(normalMessageHandler).end();

        return messageRouter;
    }
}
