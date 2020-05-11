package com.leitan.springapi.utils;

import lombok.Data;

/**
 * @Description 分页信息
 * @Author lei.tan
 * @Date 2020/5/10 20:03
 */
@Data
public class Pagination {

    /**
     * 当前页码，从1开始
     */
    private int current;

    /**
     * 每页多少条
     */
    private int pageSize;

    /**
     * 数据总条数
     */
    private long total;

    /**
     * 总页数
     */
    private int pages;

}
