package ad.home.pojo.dbentity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
@ToString()
public class UserEntity implements Serializable {
    @TableId
    private Integer userId;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 真实姓名
    private String userRealName;
    // 手机号码
    private String userPhone;
    // 用户邮箱
    private String userEmail;
    // 是否实名认证
    private Boolean isTruenameVerify;
    // 头像图片
    private String chatHeadIcon;
    // 个性签名
    private String signature;
    // 状态：1：正常；0：无效；2：删除
    private Integer userStatus;
    // 前台用户/后台用户：1：前台；2：后台
    private Integer isAdmin;
    // 最后登录时间
    private Date lastLoginTime;
    // 条记录入表时间
    private Date createTime;
    // 这条记录最后修改时间
    private Date updateTime;

}
