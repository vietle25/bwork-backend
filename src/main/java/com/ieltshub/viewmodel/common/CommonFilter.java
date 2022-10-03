package com.ieltshub.viewmodel.common;

/**
 * Created by Tuan on 6/20/18.
 */
public class CommonFilter {

    private Boolean isGetAll; //Get all data
    private Paging paging; //Paging

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Boolean getIsGetAll() {
        return isGetAll;
    }

    public void setIsGetAll(Boolean getAll) {
        isGetAll = getAll;
    }
}
