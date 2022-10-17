package com.evowork.viewmodel.common;

import java.util.List;

/**
 * @author tuannd
 * @date 14/07/2016
 * @since 1.0
 */
public class PaginationResult<T> {
	
    private Paging paging;
    private List<T> data;

    public PaginationResult() {

    }

    public PaginationResult(Paging paging, List<T> data) {
        this.paging = paging;
        this.data = data;
    }

    public PaginationResult(Paging paging) {
        this.paging = paging;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
