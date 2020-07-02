package com.xgs.hisystem.pojo.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019/4/8
 * @description:
 */
public class UserInfoVO {

    @Length(max = 20, message = "用户名不超过20个字符")
    @NotBlank(message = "不能为空")
    private String username;

    @Length(max = 2, message = "性别不超过2个字符")
    private String sex;

    @Length(max = 20, message = "生日不超过20个字符")
    private String birthday;

    @Length(max = 11, message = "电话号码不超过11个字符")
    /* @Pattern(regexp = "/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$/", message = "电话号码格式错误")*/
    private String phone;

    @Length(max = 20, message = "政治面貌不超过20个字符")
    private String politicalStatus;

    @Length(max = 30, message = "地址不超过30个字符")
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
