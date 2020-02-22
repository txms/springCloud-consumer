package com.bitsun.consumer.service.local;

import com.bitsun.consumer.common.web.ResultDTO;
import com.bitsun.consumer.dto.request.LoginReqDto;
import com.bitsun.consumer.dto.response.LoginResDto;

public interface LoginService {

    public ResultDTO<LoginResDto> login(LoginReqDto loginReqDto);
}
