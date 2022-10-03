package com.ieltshub.viewmodel.user;

public class CategoryFilter {

    private String paramSearch;
    private Long manufactureId; /// ~ parent_category_id

    public String getParamSearch() {
        return paramSearch;
    }

    public void setParamSearch(String paramSearch) {
        this.paramSearch = paramSearch;
    }

    public Long getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Long manufactureId) {
        this.manufactureId = manufactureId;
    }
}
