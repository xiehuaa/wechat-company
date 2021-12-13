package com.xh.wechat.company.controller;

import com.alibaba.fastjson.JSON;
import com.xh.wechat.company.config.WechatCompanyConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-06 19:43
 */
@Slf4j
@Api(value = "CallbackController", tags = "企业微信回调相关接口")
@RestController
@RequestMapping(value = "/api/callback")
public class CallbackController {
    @Resource
    private WechatCompanyConfig wechatCompanyConfig;

    @ApiOperation(value = "企业微信鉴权接口", httpMethod = "GET")
    @GetMapping(value = "/{agentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String auth(@PathVariable Integer agentId,
                       @RequestParam("echostr") String echostr,
                       @RequestParam("msg_signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], echostr=[\n{}\n] ",
                signature, timestamp, nonce, echostr);
        WxCpService wxCpService = wechatCompanyConfig.getWxCpService(agentId);
        if (wxCpService == null) {
            String errorMsg = String.format("未找到对应agentId=[%d]的配置，请核实！", agentId);
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (wxCpService.checkSignature(signature, timestamp, nonce, echostr)) {
            String result = new WxCpCryptUtil(wxCpService.getWxCpConfigStorage()).decrypt(echostr);
            log.info("鉴权结果为：【{}】", result);
            return result;
        }

        return "非法请求";
    }

    @ApiOperation(value = "企业微信回调接口", httpMethod = "POST")
    @PostMapping(value = "/{agentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postCallback(@PathVariable Integer agentId,
                               @RequestBody String msgBody,
                               @RequestParam("msg_signature") String signature,
                               @RequestParam("timestamp") String timestamp,
                               @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, timestamp, nonce, msgBody);
        WxCpService wxCpService = wechatCompanyConfig.getWxCpService(agentId);
        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(msgBody, wxCpService.getWxCpConfigStorage(),
                timestamp, nonce, signature);
        log.info("解密后的消息为：【{}】", JSON.toJSONString(inMessage));

        WxCpXmlOutMessage outMessage = this.route(agentId, inMessage);
        if (outMessage == null) {
            return "";
        }
        return outMessage.toEncryptedXml(wxCpService.getWxCpConfigStorage());
    }

    private WxCpXmlOutMessage route(Integer agentId, WxCpXmlMessage message) {
        try {
            return wechatCompanyConfig.getMessageRouter(agentId).route(message);
        } catch (Exception e) {
            log.error("处理消息发生异常【{}】", e.getMessage(), e);
            return null;
        }
    }
}
