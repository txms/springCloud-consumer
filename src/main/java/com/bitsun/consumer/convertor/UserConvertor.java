package com.bitsun.consumer.convertor;

import com.bitsun.consumer.dto.request.UserReqDto;
import com.bitsun.consumer.dto.response.UserResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE= Mappers.getMapper(UserConvertor.class);

    UserResDto reqToRes(UserReqDto userReqDto);

    UserResDto resToReq(UserReqDto userReqDto);
}
