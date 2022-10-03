package com.ieltshub.viewmodel.user;

import com.ieltshub.entity.Category;
import com.ieltshub.enumeration.CategoryType;
import com.ieltshub.enumeration.StatusType;

public class CategoryModel {

    private Long id;
    private StatusType status;
    private CategoryType type;
    private String name;
    private Integer value;
    private Integer seq;
    private Integer parentCategoryId;
    private String logoPath;

    public CategoryModel(Category category) {
        this.id = category.getId();
        this.status = category.getStatus();
        this.type = category.getType();
        this.name = category.getName();
        this.value = category.getValue();
        this.seq = category.getSeq();
        this.parentCategoryId = category.getParentCategoryId();
        this.logoPath = category.getLogoPath();
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
