package com.bitsun.consumer.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "登陆dto")
@Validated
public class LoginReqDto implements Serializable {

    @ApiModelProperty(value = "用户名",example = "admin")
    @NotBlank(message = "用户名为空")
    private String username;

    @ApiModelProperty(value = "密码",example = "admin")
    @NotBlank(message = "密码为空")
    private String password;

}
