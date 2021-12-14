package com.xh.wechat.company.service.impl;

import com.xh.wechat.company.domain.entity.SingleChatMessage;
import com.xh.wechat.company.mapper.SingleChatMessageMapper;
import com.xh.wechat.company.service.ISingleChatMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
