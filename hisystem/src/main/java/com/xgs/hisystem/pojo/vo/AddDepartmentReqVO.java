package com.xgs.hisystem.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author XueGuiSheng
 * @date 2020/3/23
 * @description:
 */
@ApiModel
public class AddDepartmentReqVO {

    @ApiModelProperty(value = "科室名称",example = "科室名称")
    @NotBlank(message = "科室名称不能为空！")
    private String name;

    @ApiModelProperty(value = "科室地址，例：门诊大楼二楼D区",example = "科室地址，例：门诊大楼二楼D区")
    @NotBlank(message = "科室地址不能为空！")
    private String address;

    @ApiModelProperty(value = "科室类型，0普通 1急诊，默认普通",example = "科室类型，0普通 1急诊，默认普通")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
