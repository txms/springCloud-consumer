package com.bitsun.consumer.controller.user;


import com.bitsun.consumer.dto.request.UserReqDto;
import com.bitsun.consumer.dto.response.UserResDto;
import com.bitsun.consumer.service.local.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户接口")
@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("保存用户")
    @PostMapping(value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(message = "ok",response = UserResDto.class,code = 200)
    })
    public UserResDto saveUser(UserReqDto userReqDto){
        UserResDto userResDto=userService.saveUser(userReqDto);
        return userResDto;
    };

    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(message = "ok",response = UserResDto.class,code = 200)
    })
    public UserResDto getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    };

    @ApiOperation("批量查询用户")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "用户id集合",required = true,paramType = "query",dataType = "string")
    })
    public List<UserResDto> getUserList(@RequestParam("ids") String ids){
        return userService.getUserList(ids);
    };

    @ApiOperation("修改用户信息")
    @PatchMapping("/{id}")
    @ApiResponses({
            @ApiResponse(message = "ok",response = UserResDto.class,code = 200)
    })
    public UserResDto updateUser(@PathVariable("id") Long id,@RequestBody UserReqDto userReqDto){
        return userService.updateUser(id,userReqDto);
    };

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    };

}
