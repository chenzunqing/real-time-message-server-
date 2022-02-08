package ltd.ihk.platform.rtms.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Czq
 * @Date: 2022/1/21 17:19
 * @Description TODO
 */
@Data
public class LoginUser {
    private String id;
    private String orgCode;
    private Integer delFlag;
    private Integer activitiSync;
    private Integer userIdentity;
    private String departIds;
    private String post;
    private String telephone;
    private String relTenantIds;
    private String clientId;
    private String realname;
    private String avatar;
    private Date birthday;
    private Date createTime;
    private int sex;
    private Integer userType;
    private Long userId;
    private Integer platformType;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String countryCode;
    private String phone;
    private Integer orderNums;
    private Integer commodityNum;
    private Double orderMoney;
    private Integer depositStatus;
    private Integer memberStatus;
    private Date memberBeginTime;
    private Date memberEndTime;
    private Integer status;
    private String clientToken;
    private String facebookId;
    private String googleId;
    private String appleId;
    private Integer accountFromPlatform;
    private Integer accountType;
    private String userSource;
}
