package com.jero.http.page;

import java.util.List;

/**
 * @Description 分页返回器
 * @Author lixuetao
 * @Date 2020/3/23
 **/
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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
