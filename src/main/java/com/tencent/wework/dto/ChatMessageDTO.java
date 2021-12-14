package com.tencent.wework.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 16:49
 */
@Setter
@Getter
public class ChatMessageDTO implements Serializable {
    private String msgId;
    private String action;
    private String from;
    private List<String> toList;
    private String roomId;
    private Date msgTime;
    private String msgType;

    private Long seq;

    private String body;
    private String fileUrl;
}
