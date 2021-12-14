package com.tencent.wework.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 16:48
 */
@Setter
@Getter
public class ChatDataResultDTO extends ChatBaseResultDTO {
    @JSONField(name = "chatdata")
    private List<ChatDataDTO> chatDataList;
}
