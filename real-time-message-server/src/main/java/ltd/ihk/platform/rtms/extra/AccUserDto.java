package ltd.ihk.platform.rtms.extra;

import lombok.Data;

import java.util.Date;

/**
 * @author jingyk
 */
@Data
public class AccUserDto {
    private Long id;
    private String nickName;
    private String realname;
    private String avatar;
    private Date birthday;
    private Integer sex;
    private String deviceVersion;
    private String appVersion;
    private String userNetwork;
    private String encodeStr;
    private String verCode;
    private Integer loginType;
    private Long businessId;
    private Long platformType;
    private String city;
    private String address;
    private String jpushId;
    private Integer serviceType;
    private Integer tyro;
}
