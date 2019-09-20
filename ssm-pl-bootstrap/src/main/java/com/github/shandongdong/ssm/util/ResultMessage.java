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
public class ResultMessage {
    private boolean error;
    private Integer code;
    private String message;
    private Object data;
    private Map<String, Object> businessStatus;  // 业务自己定义的返回信息


    private ResultMessage() {
    }

    public static ResultMessage success() {
        return success(0, "success", null, null);
    }

    public static ResultMessage success(Object data) {
        return success(0, "success", data, null);
    }

    public static ResultMessage success(Object data, Map<String, Object> businessStatus) {
        return success(0, "success", data, businessStatus);
    }

    public static ResultMessage failed(String message) {
        return failed(-1, message, null, null);
    }

    public static ResultMessage failed(String message, Object data) {
        return failed(-1, message, data, null);
    }

    public static ResultMessage failed(String message, Map<String, Object> businessStatus) {
        return failed(-1, message, null, businessStatus);
    }

    public static ResultMessage failed(String message, Object data, Map<String, Object> businessStatus) {
        return failed(-1, message, data, businessStatus);
    }

    public static ResultMessage success(Integer code, String message, Object data, Map<String, Object> businessStatus) {
        ResultMessage ResultMessage = new ResultMessage();
        ResultMessage.setError(false);
        ResultMessage.setCode(code);
        ResultMessage.setMessage(message);
        ResultMessage.setData(data);
        ResultMessage.setBusinessStatus(businessStatus);
        return ResultMessage;
    }

    public static ResultMessage failed(Integer code, String message, Object data, Map<String, Object> businessStatus) {
        ResultMessage ResultMessage = new ResultMessage();
        ResultMessage.setError(true);
        ResultMessage.setCode(code);
        ResultMessage.setMessage(message);
        ResultMessage.setData(data);
        ResultMessage.setBusinessStatus(businessStatus);
        return ResultMessage;
    }

}
