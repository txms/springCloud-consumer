package com.bitsun.consumer.service.local;

import com.bitsun.consumer.dto.request.UserReqDto;
import com.bitsun.consumer.dto.response.UserResDto;

import java.util.List;

public interface UserService {

    UserResDto saveUser(UserReqDto userReqDto);

    UserResDto getUser(Long id);

    List<UserResDto> getUserList(String ids);

    UserResDto updateUser(Long id,UserReqDto userReqDto);

    void deleteUser(Long id);
}
