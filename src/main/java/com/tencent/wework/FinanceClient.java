package com.tencent.wework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wework.dto.ChatDataDTO;
import com.tencent.wework.dto.ChatDataResultDTO;
import com.tencent.wework.dto.ChatMessageDTO;
import com.xh.wechat.company.constant.ChatMessageConstant;
import com.xh.wechat.company.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 16:52
 */
@Slf4j
public class FinanceClient {
    private long sdk;
    private String privateKey;

    private FinanceClient() {

    }

    /**
     * 创建FinanceClient对象
     *
     * @param corpId     企业id
     * @param secret     企业secret
     * @param privateKey 私钥
     * @return FinanceClient对象
     */
    public static FinanceClient newInstance(String corpId, String secret, String privateKey) {
        long sdk = Finance.NewSdk();
        Finance.Init(sdk, corpId, secret);

        FinanceClient financeClient = new FinanceClient();
        financeClient.privateKey = privateKey;
        financeClient.sdk = sdk;

        return financeClient;
    }

    /**
     * 获取聊天记录列表
     *
     * @param seq   查询偏移量
     * @param limit 查询条数
     * @return 聊天记录列表
     */
    public List<ChatMessageDTO> getChatMessageList(long seq, long limit) {
        List<ChatDataDTO> chatDataList = this.getChatDataList(seq, limit);
        if (CollectionUtils.isEmpty(chatDataList)) {
            return Collections.emptyList();
        }

        List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();
        for (ChatDataDTO chatDataDTO : chatDataList) {
            ChatMessageDTO chatMessageDTO = this.convertChatData2ChatMessage(chatDataDTO);

            chatMessageDTOList.add(chatMessageDTO);
        }
        return chatMessageDTOList;
    }

    /**
     * 获取chatData列表
     *
     * @param seq   查询偏移量
     * @param limit 查询条数
     * @return chatData列表
     */
    private List<ChatDataDTO> getChatDataList(long seq, long limit) {
        long slice = Finance.NewSlice();
        int ret = Finance.GetChatData(sdk, seq, limit, "", "", 100, slice);
        if (ret != 0) {
            log.error("获取企业微信聊天记录失败，ret：【{}】", ret);
            return Collections.emptyList();
        }
        String result = Finance.GetContentFromSlice(slice);
        ChatDataResultDTO chatDataResultDTO = JSON.parseObject(result, ChatDataResultDTO.class);

        if (chatDataResultDTO.isSuccess()) {
            return chatDataResultDTO.getChatDataList();
        } else {
            log.error("获取企业微信聊天记录失败，错误码为：【{}】，错误信息为：【{}】", chatDataResultDTO.getErrCode(), chatDataResultDTO.getErrMsg());
            return Collections.emptyList();
        }
    }

    /**
     * 将chatData对象转换为chatMessage
     *
     * @param chatDataDTO ChatData对象
     * @return ChatMessage对象
     */
    private ChatMessageDTO convertChatData2ChatMessage(ChatDataDTO chatDataDTO) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setSeq(chatDataDTO.getSeq());
        chatMessageDTO.setMsgId(chatDataDTO.getMsgId());

        String text = this.decryptData(chatDataDTO.getEncryptRandomKey(), chatDataDTO.getEncryptChatMsg());
        if (StringUtils.isNotBlank(text)) {
            JSONObject jsonObject = JSON.parseObject(text);
            chatMessageDTO.setAction(jsonObject.getString("action"));
            chatMessageDTO.setMsgType(jsonObject.getString("msgtype"));
            Long msgTimeStamp = jsonObject.getLong("msgtime");
            if (msgTimeStamp != null) {
                chatMessageDTO.setMsgTime(new Date(msgTimeStamp));
            }

            chatMessageDTO.setFrom(jsonObject.getString("from"));
            String toListStr = jsonObject.getString("tolist");
            if (StringUtils.isNotBlank(toListStr)) {
                chatMessageDTO.setToList(JSON.parseArray(toListStr, String.class));
            }
            chatMessageDTO.setRoomId(jsonObject.getString("roomid"));

            JSONObject bodyJsonObject;
            String msgType = chatMessageDTO.getMsgType();
            if (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_DOC)) {
                bodyJsonObject = jsonObject.getJSONObject("doc");
            } else if (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_EXTERNAL_RED_PACKET)) {
                bodyJsonObject = jsonObject.getJSONObject("redpacket");
            } else {
                bodyJsonObject = jsonObject.getJSONObject(msgType);
            }
            if (bodyJsonObject != null) {
                chatMessageDTO.setBody(bodyJsonObject.toJSONString());
            }
        }

        return chatMessageDTO;
    }

    /**
     * 解析加密的消息
     *
     * @param encryptRandomKey 企业微信返回的randomKey
     * @param encryptChatMsg   加密的消息
     * @return 解密后的消息体
     */
    private String decryptData(String encryptRandomKey, String encryptChatMsg) {
        try {
            String encryptKey = RSAUtil.decrypt(encryptRandomKey, privateKey);
            long slice = Finance.NewSlice();
            int ret = Finance.DecryptData(sdk, encryptKey, encryptChatMsg, slice);
            if (ret != 0) {
                log.info("解析企业微信聊天记录失败,ret:【{}】", ret);
                return "";
            }
            String text = Finance.GetContentFromSlice(slice);
            Finance.FreeSlice(slice);

            return text;
        } catch (Exception e) {
            log.error("解密企业微信聊天记录发生异常：", e);
            return "";
        }
    }
}
