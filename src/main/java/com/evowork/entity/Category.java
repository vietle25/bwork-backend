package com.evowork.entity;


import com.evowork.enumeration.CategoryType;
import com.evowork.enumeration.StatusType;
import com.evowork.enumeration.converter.CategoryTypeConverter;
import com.evowork.enumeration.converter.StatusTypeConverter;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "status")
    @Convert(converter = StatusTypeConverter.class)
    private StatusType status;

    @Column(name = "type")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType type;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Integer value;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    @Column(name = "logo_path")
    private String logoPath;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
