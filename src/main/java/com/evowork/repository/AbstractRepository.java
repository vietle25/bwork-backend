package com.evowork.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.evowork.viewmodel.common.Paging;

/**
 * @author tuannd
 * @date 29/06/2016
 * @since 1.0
 */
public abstract class AbstractRepository {
	
	@Autowired
    protected Repository repo;

    protected String getPagingSql(Paging paging) {
        return this.getPagingSql(paging.getPage(), paging.getPageSize());
    }

    protected String getPagingSql(Integer page, Integer pageSize) {
        return String.format("limit %s offset %s", pageSize, page * pageSize);
    }
}
