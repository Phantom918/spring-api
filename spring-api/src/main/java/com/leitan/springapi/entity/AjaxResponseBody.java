package com.leitan.springapi.entity;

import com.github.pagehelper.Page;
import com.leitan.springapi.utils.Pagination;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author tanlei
 * @Description 响应信息封装
 */
@Data
public class AjaxResponseBody implements Serializable {

    /**
     * 状态码
     */
    private int status;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 数据结果
     */
    private Object result;

    /**
     * token 凭证
     */
    private String token;

    /**
     * 分页信息
     */
    private Pagination pagination;


    /**
     * 成功
     *
     * @return
     */
    public static AjaxResponseBody success() {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setStatus(HttpStatus.OK.value());
        responseBody.setMessage("操作成功！");
        return responseBody;
    }

    /**
     * 成功
     *
     * @return
     */
    public static AjaxResponseBody success(String message) {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setStatus(HttpStatus.OK.value());
        responseBody.setMessage(message);
        return responseBody;
    }

    /**
     * 无权限
     *
     * @return
     */
    public static AjaxResponseBody forbidden() {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setStatus(HttpStatus.FORBIDDEN.value());
        responseBody.setMessage("无权限访问！");
        return responseBody;
    }

    /**
     * 入参异常
     *
     * @return
     */
    public static AjaxResponseBody parameterError() {
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setStatus(HttpStatus.BAD_REQUEST.value());
        responseBody.setMessage("方法请求参数异常！");
        return responseBody;
    }


    public void setResult(Object result) {
        this.result = result;
        if (result instanceof Page) {
            Page data = (Page) result;
            pagination = new Pagination();
            pagination.setCurrent(data.getPageNum());
            pagination.setPageSize(data.getPageSize());
            pagination.setTotal(data.getTotal());
            pagination.setPages(data.getPages());
        }
    }
}
