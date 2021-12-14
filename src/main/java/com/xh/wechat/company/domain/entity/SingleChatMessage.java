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
 * 企业微信单聊消息
 * </p>
 *
 * @author XieHua
 * @since 2021-12-14
 */
@Getter
@Setter
@TableName("pe_single_chat_message")
public class SingleChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息id
     */
    @TableField("msg_id")
    private String msgId;

    /**
     * 消息动作send/recall/switch
     */
    @TableField("action")
    private String action;

    /**
     * 企业微信成员id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 企业微信外部联系人id
     */
    @TableField("external_user_id")
    private String externalUserId;

    /**
     * 发送方 1成员 2外部联系人
     */
    @TableField("from_type")
    private Integer fromType;

    /**
     * 消息类型
     */
    @TableField("msg_type")
    private String msgType;

    /**
     * 消息发送时间
     */
    @TableField("send_time")
    private Date sendTime;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 文件地址
     */
    @TableField("file_url")
    private String fileUrl;

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

    public static final String MSG_ID = "msg_id";

    public static final String ACTION = "action";

    public static final String USER_ID = "user_id";

    public static final String EXTERNAL_USER_ID = "external_user_id";

    public static final String FROM_TYPE = "from_type";

    public static final String MSG_TYPE = "msg_type";

    public static final String SEND_TIME = "send_time";

    public static final String CONTENT = "content";

    public static final String FILE_URL = "file_url";

    public static final String CREATED_TIME = "created_time";

    public static final String MODIFIED_TIME = "modified_time";

}
