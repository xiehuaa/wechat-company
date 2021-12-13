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
 * 成员对外属性表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-09
 */
@Getter
@Setter
@TableName("pe_user_external_attribute")
public class UserExternalAttribute implements Serializable {

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
     * 类型 0文本 1网页 2小程序
     */
    @TableField("type")
    private Integer type;

    /**
     * 属性名称
     */
    @TableField("name")
    private String name;

    /**
     * 文本属性值
     */
    @TableField("value")
    private String value;

    /**
     * 网页的url
     */
    @TableField("url")
    private String url;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 小程序appid
     */
    @TableField("app_id")
    private String appId;

    /**
     * 小程序页面路径
     */
    @TableField("page_path")
    private String pagePath;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 是否删除 0否 1是
     */
    @TableField("is_delete")
    private Integer isDelete;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String TYPE = "type";

    public static final String NAME = "name";

    public static final String VALUE = "value";

    public static final String URL = "url";

    public static final String TITLE = "title";

    public static final String APP_ID = "app_id";

    public static final String PAGE_PATH = "page_path";

    public static final String CREATED_TIME = "created_time";

    public static final String IS_DELETE = "is_delete";

}
