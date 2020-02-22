package com.bitsun.consumer.common.web;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDTO <T> implements Serializable {


    private String code;

    private String msg;

    private T data;

    public static <T> ResultDTO ok(){
        ResultDTO dto=new ResultDTO();
        dto.code="0";
        dto.msg="success";
        return dto;
    }

    public static <T> ResultDTO ok(T data){
        ResultDTO dto=new ResultDTO();
        dto.code="0";
        dto.msg="success";
        dto.data=data;
        return dto;
    }

    public static <T> ResultDTO error(String code,String msg){
        ResultDTO dto=new ResultDTO();
        dto.code=code;
        dto.msg=msg;
        return dto;
    }

    public static <T> ResultDTO error(){
        ResultDTO dto=new ResultDTO();
        dto.code="500";
        dto.msg="未知异常,请联系管理员";
        return dto;
    }


}
