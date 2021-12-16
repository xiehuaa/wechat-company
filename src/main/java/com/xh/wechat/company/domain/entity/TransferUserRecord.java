package com.xh.wechat.company.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 接替客户记录表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-16
 */
@Getter
@Setter
@TableName("pe_transfer_user_record")
public class TransferUserRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被接替的成员id
     */
    @TableField("handover_user_id")
    private String handoverUserId;

    /**
     * 接替的成员id
     */
    @TableField("takeover_user_id")
    private String takeoverUserId;

    /**
     * 外部联系人id
     */
    @TableField("external_user_id")
    private String externalUserId;

    /**
     * 接替类型 1在职 2离职
     */
    @TableField("transfer_type")
    private Integer transferType;

    /**
     * 接替响应码
     */
    @TableField("result_code")
    private Integer resultCode;

    /**
     * 接替响应消息
     */
    @TableField("result_msg")
    private String resultMsg;

    /**
     * 是否成功 0否 1是
     */
    @TableField("is_success")
    private Integer isSuccess;

    /**
     * 失败原因
     */
    @TableField("fail_reason")
    private String failReason;

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


    public static final String ID = "id";

    public static final String HANDOVER_USER_ID = "handover_user_id";

    public static final String TAKEOVER_USER_ID = "takeover_user_id";

    public static final String EXTERNAL_USER_ID = "external_user_id";

    public static final String TRANSFER_TYPE = "transfer_type";

    public static final String RESULT_CODE = "result_code";

    public static final String RESULT_MSG = "result_msg";

    public static final String IS_SUCCESS = "is_success";

    public static final String FAIL_REASON = "fail_reason";

    public static final String CREATED_TIME = "created_time";

    public static final String MODIFIED_TIME = "modified_time";

}
