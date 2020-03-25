package com.jero.core;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jero.http.page.PageInfo;
import org.apache.poi.ss.formula.functions.T;

/**
 * 基础控制器，包含处理分页等方法
 *
 * @author lixuetao
 * @date 2020-03-25
 */
public class BaseController {

    public PageInfo<T> getPageInfo(IPage<T> page) {
        PageInfo<T> pageInfo = new PageInfo();
        pageInfo.setList(page.getRecords());
        pageInfo.setCount(page.getTotal());
        pageInfo.setPageSize((int)page.getSize());
        pageInfo.setPageCount(page.getPages());
        pageInfo.setPageNo((int)page.getCurrent());
        return pageInfo;
    }


}
