package com.bitsun.consumer.service.local.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bitsun.consumer.common.util.RedisUtil;
import com.bitsun.consumer.convertor.UserConvertor;
import com.bitsun.consumer.dto.request.UserReqDto;
import com.bitsun.consumer.dto.response.UserResDto;
import com.bitsun.consumer.service.local.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "c1")
public class UserServiceImpl implements UserService {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 保存用户
     * @param userReqDto
     * @return
     */
    public UserResDto saveUser(UserReqDto userReqDto){
        UserResDto userResDto= UserConvertor.INSTANCE.reqToRes(userReqDto);
        return userResDto;
    };

    /**
     * 查询用户
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    public UserResDto getUser(Long id){
        UserResDto userResDto=new UserResDto();
        userResDto.setName("张三");
        userResDto.setId(id);
        System.out.println("111");
        return userResDto;

    }

    /**
     * 批量查询用户列表
     * @param ids
     * @return
     */
    public List<UserResDto> getUserList(List<Long> ids){
        List<UserResDto> userResDtoList=new ArrayList<>();
        return userResDtoList;
    };


    @CachePut(key = "#id")
    public UserResDto updateUser(Long id,UserReqDto userReqDto){
        UserResDto userResDto=new UserResDto();
        userResDto.setName("李四");
        System.out.println("111");
        return userResDto;
    }

    //查询用户列表
    public List<UserResDto> getUserList(String ids){
        //idList列表
        List<String> idList= Arrays.asList(ids.split(","));
        List<String> redisIdList=new ArrayList<>();
        idList.forEach(id->{
            String key="c1::"+id;
            redisIdList.add(key);
        });

        List<UserResDto> userResDtoList=new ArrayList<>();

        List existUser=redisUtil.mgetList(redisIdList);

        existUser.forEach(user->{
           // UserResDto userResDto= JSONObject.parseObject(user.toString(),UserResDto.class);
            if(!StringUtils.isEmpty(user)){
                UserResDto userResDto= JSONObject.parseObject(JSON.toJSONString(user),UserResDto.class);
                userResDtoList.add(userResDto);
            }
        });

        List<Long> userIdList= idList.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
        //已存在的id
        List<Long> existIdList=userResDtoList.stream().map(userResDto -> userResDto.getId()).collect(Collectors.toList());

        //不存在的id列表
        userIdList.removeAll(existIdList);
        userIdList.forEach(id->{
            UserResDto userResDto=new UserResDto();
            userResDto.setId(id);
            userResDto.setName("王五");
            redisUtil.set("c1::"+id,userResDto);
            userResDtoList.add(userResDto);
        });
        return userResDtoList;
    };

    @CacheEvict(key = "#id")
    public void deleteUser(Long id){

    }


}
