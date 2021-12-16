package com.xh.wechat.company.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wework.FinanceClient;
import com.tencent.wework.dto.ChatMessageDTO;
import com.xh.wechat.company.config.WechatCompanyProperties;
import com.xh.wechat.company.controller.RedisConstant;
import com.xh.wechat.company.domain.entity.SingleChatMessage;
import com.xh.wechat.company.mapper.SingleChatMessageMapper;
import com.xh.wechat.company.redis.RedisClient;
import com.xh.wechat.company.service.ISingleChatMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 企业微信单聊消息 服务实现类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-14
 */
@Service
public class SingleChatMessageServiceImpl extends ServiceImpl<SingleChatMessageMapper, SingleChatMessage> implements ISingleChatMessageService {
    @Resource
    private WechatCompanyProperties wechatCompanyProperties;

    @Resource
    private RedisClient redisClient;

    @Override
    public Boolean syncChatMessage(Long seq) {
        if (seq == null) {
            Object redisValue = redisClient.get(RedisConstant.CHAT_MESSAGE_SEQ_KEY);
            if (redisValue != null) {
                seq = Long.parseLong(String.valueOf(redisValue));
            } else {
                seq = 0L;
            }
        }

        long limit = 400L;
        FinanceClient financeClient = FinanceClient.newInstance(wechatCompanyProperties.getCorpId(), wechatCompanyProperties.getSecret(), wechatCompanyProperties.getPrivateKey());
        while (true) {
            List<ChatMessageDTO> chatMessageList = financeClient.getChatMessageList(seq, limit);
            if (CollectionUtils.isEmpty(chatMessageList)) {
                break;
            }
            for (ChatMessageDTO chatMessageDTO : chatMessageList) {
                // wb开头代表为机器人  _external结尾为外部消息
                if (chatMessageDTO.getFrom().startsWith("wb") || !chatMessageDTO.getMsgId().endsWith("_external")) {
                    continue;
                }
                // 群组消息不处理
                if (StringUtils.isNotBlank(chatMessageDTO.getRoomId())) {
                    continue;
                }

                SingleChatMessage chatMessage = this.convertFromChatMessage(chatMessageDTO);
                this.save(chatMessage);
            }
            seq = chatMessageList.get(chatMessageList.size() - 1).getSeq();
            redisClient.set(RedisConstant.CHAT_MESSAGE_SEQ_KEY, seq);

            if (chatMessageList.size() < limit) {
                break;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 转换为SingleChatMessage对象
     *
     * @param chatMessageDTO ChatMessageDTO对象
     * @return SingleChatMessage对象
     */
    private SingleChatMessage convertFromChatMessage(ChatMessageDTO chatMessageDTO) {
        SingleChatMessage chatMessage = new SingleChatMessage();

        chatMessage.setMsgId(chatMessageDTO.getMsgId());
        chatMessage.setMsgType(chatMessageDTO.getMsgType());
        chatMessage.setSeq(chatMessageDTO.getSeq());
        chatMessage.setFileUrl(chatMessageDTO.getFileUrl());
        chatMessage.setContent(chatMessageDTO.getBody());

        if (chatMessageDTO.getFrom().startsWith("wo") || chatMessageDTO.getFrom().startsWith("wm")) {
            chatMessage.setFromType(2);
            chatMessage.setExternalUserId(chatMessageDTO.getFrom());
            chatMessage.setUserId(chatMessageDTO.getToList().get(0));
        } else {
            chatMessage.setFromType(1);
            chatMessage.setUserId(chatMessageDTO.getFrom());
            chatMessage.setExternalUserId(chatMessageDTO.getToList().get(0));
        }

        chatMessage.setSendTime(chatMessageDTO.getMsgTime());
        // TODO: 异步处理聊天记录中的文件

        return chatMessage;
    }
}
