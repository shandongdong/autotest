package com.github.shandongdong.ssm.util;

import lombok.Data;

import java.util.Map;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 14:51
 * @Email: shandongdong@126.com
 * @Description: 统一返回格式
 **/
@Data
public class ResponseResult {
    private boolean error;
    private Integer code;
    private String message;
    private Object data;
    private Map<String, Object> businessStatus;  // 业务自己定义的返回信息


    private ResponseResult() {
    }

    public static ResponseResult success() {
        return success(0, "success", null, null);
    }

    public static ResponseResult success(Object data) {
        return success(0, "success", data, null);
    }

    public static ResponseResult success(Object data, Map<String, Object> businessStatus) {
        return success(0, "success", data, businessStatus);
    }

    public static ResponseResult failed(String message) {
        return failed(-1, message, null, null);
    }

    public static ResponseResult failed(String message, Object data) {
        return failed(-1, message, data, null);
    }

    public static ResponseResult failed(String message, Map<String, Object> businessStatus) {
        return failed(-1, message, null, businessStatus);
    }

    public static ResponseResult failed(String message, Object data, Map<String, Object> businessStatus) {
        return failed(-1, message, data, businessStatus);
    }

    public static ResponseResult success(Integer code, String message, Object data, Map<String, Object> businessStatus) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setError(false);
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(data);
        responseResult.setBusinessStatus(businessStatus);
        return responseResult;
    }

    public static ResponseResult failed(Integer code, String message, Object data, Map<String, Object> businessStatus) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setError(true);
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(data);
        responseResult.setBusinessStatus(businessStatus);
        return responseResult;
    }

}
