package com.tencent.wework.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 16:47
 */
@Setter
@Getter
public class ChatDataDTO implements Serializable {
    private Long seq;

    @JSONField(name = "msgid")
    private String msgId;

    @JSONField(name = "publickey_ver")
    private String publicKeyVer;

    @JSONField(name = "encrypt_random_key")
    private String encryptRandomKey;

    @JSONField(name = "encrypt_chat_msg")
    private String encryptChatMsg;
}
