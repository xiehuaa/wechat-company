package com.xh.wechat.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.wechat.company.domain.entity.SingleChatMessage;

/**
 * <p>
 * 企业微信单聊消息 服务类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-14
 */
public interface ISingleChatMessageService extends IService<SingleChatMessage> {
    /**
     * 同步单聊消息
     *
     * @param seq 消息偏移量
     * @return 是否操作成功
     */
    Boolean syncChatMessage(Long seq);
}
