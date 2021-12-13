package com.xh.wechat.company.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 外部联系人表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Getter
@Setter
@TableName("pe_external_user")
public class ExternalUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 外部联系人id
     */
    @TableField("external_user_id")
    private String externalUserId;

    /**
     * 外部联系人名称
     */
    @TableField("name")
    private String name;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 类型 1微信 2企业微信
     */
    @TableField("type")
    private Integer type;

    /**
     * 性别 0未知 1男 2女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 微信开放平台的身份标识
     */
    @TableField("union_id")
    private String unionId;


    public static final String ID = "id";

    public static final String EXTERNAL_USER_ID = "external_user_id";

    public static final String NAME = "name";

    public static final String AVATAR = "avatar";

    public static final String TYPE = "type";

    public static final String GENDER = "gender";

    public static final String UNION_ID = "union_id";

}
