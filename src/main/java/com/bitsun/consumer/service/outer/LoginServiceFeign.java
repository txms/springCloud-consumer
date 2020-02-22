package com.bitsun.consumer.service.outer;


import com.bitsun.consumer.common.web.ResultDTO;
import com.bitsun.consumer.dto.request.LoginReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "provider")
public interface LoginServiceFeign {

    /**
     * 登陆
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultDTO login(@RequestBody LoginReqDto loginReqDto);
}
