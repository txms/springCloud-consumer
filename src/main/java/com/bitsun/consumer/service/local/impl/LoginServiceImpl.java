package com.bitsun.consumer.service.local.impl;

import com.bitsun.consumer.common.web.ResultDTO;
import com.bitsun.consumer.dto.request.LoginReqDto;
import com.bitsun.consumer.dto.response.LoginResDto;
import com.bitsun.consumer.service.outer.LoginServiceFeign;
import com.bitsun.consumer.service.local.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginServiceFeign loginServiceFeign;

    @Override
    public ResultDTO<LoginResDto> login(LoginReqDto loginReqDto) {

        ResultDTO resultDTO=loginServiceFeign.login(loginReqDto);

        return resultDTO;
    }
}
