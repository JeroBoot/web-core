package com.jero.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jero.http.page.PageInfo;
import com.jero.mp.entity.BaseEntity;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 基础控制器，包含处理分页等方法
 *
 * @author lixuetao
 * @date 2020-03-25
 */
public abstract class BaseController<T extends BaseEntity<?>> {

    public BaseController(){}

    public final T getById(Serializable id){
        return this.getService().getById(id);
    }

    public final List<T> list(T t){
        return this.getService().list(Wrappers.query(t));
    }

    public final PageInfo<T> page(PageInfo pageInfo, T t){
        Page page = getPage(pageInfo);
        Page resultPage = this.getService().page(page, Wrappers.query(t));
        return getPageInfo(resultPage);
    }

    public final boolean save(T data){
        return this.getService().save(data);
    }

    public final boolean update(T data){
        return this.getService().updateById(data);
    }

    public final boolean deleteBatchIds(Serializable... ids){
        return this.getService().removeByIds(Arrays.asList(ids));
    }

    public PageInfo<T> getPageInfo(IPage<T> page) {
        PageInfo<T> pageInfo = new PageInfo();
        pageInfo.setList(page.getRecords());
        pageInfo.setCount(page.getTotal());
        pageInfo.setPageSize((int)page.getSize());
        pageInfo.setPageCount(page.getPages());
        pageInfo.setPageNo((int)page.getCurrent());
        return pageInfo;
    }

    public Page getPage(PageInfo pageInfo) {
        Page page = new Page();
        page.setSize(pageInfo.getPageSize());
        page.setCurrent(pageInfo.getPageNo());
        page.setPages(pageInfo.getPageCount());

        return page;
    }

    protected abstract IService<T> getService();

}
