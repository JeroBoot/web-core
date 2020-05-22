package com.jero.http.page;

import lombok.Data;

import java.util.List;

/**
 * @Description 分页返回器
 * @Author lixuetao
 * @Date 2020/3/23
 **/
@Data
public class PageInfo<T> {

    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页数据量
     */
    private Integer pageSize;
    /**
     * 总数据量
     */
    private Long count;
    /**
     * 当前分页总页数
     */
    private Long pageCount;

    private List<T> list;
}
