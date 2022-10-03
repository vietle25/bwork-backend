package com.ieltshub.viewmodel.company;

import com.ieltshub.viewmodel.common.Paging;

public class CompanyFilter {

    private Paging paging;
    private String stringSearch;

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String getStringSearch() {
        return stringSearch;
    }

    public void setStringSearch(String stringSearch) {
        this.stringSearch = stringSearch;
    }
}
