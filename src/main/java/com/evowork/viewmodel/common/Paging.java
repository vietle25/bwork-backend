package com.evowork.viewmodel.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author tuannd
 * @date 12/05/2016
 * @since 1.0
 */
public class Paging {
    private Integer page = 0;
    private Integer pageSize = 10;
    private Integer total;

    public Paging() {

    }

    public Paging(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    @JsonIgnore
    public Boolean isValidPaging() {
        return this.page != null && this.page >= 0 && (this.pageSize == null || this.pageSize >= 0);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
