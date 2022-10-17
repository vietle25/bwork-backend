package com.evowork.viewmodel.user;

import com.evowork.viewmodel.common.Paging;

public class NotificationFilter {

    private Long userId; //User id
    private Paging paging; //Paging
    private String stringSearch; // string for search
    private Integer type; // type of notification

    public String getStringSearch() {
        return stringSearch;
    }

    public void setStringSearch(String stringSearch) {
        this.stringSearch = stringSearch;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

