package com.bitsun.consumer.controller.login;

import com.bitsun.consumer.common.constant.CommonConstant;
import com.bitsun.consumer.common.web.ResultDTO;
import com.bitsun.consumer.dto.response.LoginResDto;
import com.bitsun.consumer.dto.request.LoginReqDto;
import com.bitsun.consumer.service.local.LoginService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@ApiOperation(value = "用户登陆")
public class Login {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    @HystrixCommand(fallbackMethod = "fallbackError")
    @ApiOperation(value = "用户登陆")
    public ResultDTO<LoginResDto> login(@RequestBody @Valid LoginReqDto loginReqDto){

        ResultDTO<LoginResDto> resultDTO=loginService.login(loginReqDto);

        if(CommonConstant.SUCCESS_CODE.equals(resultDTO.getCode())){
            return ResultDTO.ok();
        }
        return ResultDTO.error();
    }


    @PostMapping("/loginOut")
    @ApiOperation(value = "用户登出")
    public ResultDTO loginOut(@RequestBody LoginReqDto loginReqDto){

        return ResultDTO.ok();

    }

    public ResultDTO  fallbackError(LoginReqDto loginReqDto){
        return ResultDTO.error("进入熔断","500");
    }
}
