package com.xh.wechat.company.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 外部联系人和成员关系表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Getter
@Setter
@TableName("pe_external_user_follow")
public class ExternalUserFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 成员id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 外部联系人id
     */
    @TableField("external_user_id")
    private String externalUserId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 备注的企业名称
     */
    @TableField("remark_corp_name")
    private String remarkCorpName;

    /**
     * 备注的手机号
     */
    @TableField("remark_mobiles")
    private String remarkMobiles;

    /**
     * 添加方式
     */
    @TableField("add_way")
    private Integer addWay;

    /**
     * 添加时间
     */
    @TableField("add_time")
    private Date addTime;

    /**
     * 发起添加的用户id
     */
    @TableField("oper_user_id")
    private String operUserId;

    /**
     * 企业自定义的state
     */
    @TableField("state")
    private String state;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField("modified_time")
    private Date modifiedTime;

    /**
     * 是否删除 0否 1是
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 删除方 1成员 2外部联系人
     */
    @TableField("delete_by")
    private Integer deleteBy;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String EXTERNAL_USER_ID = "external_user_id";

    public static final String REMARK = "remark";

    public static final String DESCRIPTION = "description";

    public static final String REMARK_CORP_NAME = "remark_corp_name";

    public static final String REMARK_MOBILES = "remark_mobiles";

    public static final String ADD_WAY = "add_way";

    public static final String ADD_TIME = "add_time";

    public static final String OPER_USER_ID = "oper_user_id";

    public static final String STATE = "state";

    public static final String CREATED_TIME = "created_time";

    public static final String MODIFIED_TIME = "modified_time";

    public static final String IS_DELETE = "is_delete";

    public static final String DELETE_BY = "delete_by";

}
