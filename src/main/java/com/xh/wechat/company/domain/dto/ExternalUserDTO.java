package com.xh.wechat.company.domain.dto;

import com.xh.wechat.company.domain.entity.ExternalUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 16:47
 */
@Setter
@Getter
public class ExternalUserDTO extends ExternalUser {
    /**
     * 成员关系列表
     */
    List<ExternalUserFollowDTO> followList;
}
