package com.bitsun.consumer.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserReqDto implements Serializable {

    @ApiModelProperty(value = "用户名",example = "admin")
    private String username;

    @ApiModelProperty(value = "密码",example = "admin")
    private String password;

    @ApiModelProperty(value = "姓名",example = "张三")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty("手机号")
    private String phone;


}
