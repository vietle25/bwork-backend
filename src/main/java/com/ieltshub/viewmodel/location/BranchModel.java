package com.ieltshub.viewmodel.location;

import com.ieltshub.entity.Branch;

public class BranchModel {

    private Long id;
    private String name;

    public BranchModel(Branch branch) {
        id = branch.getId();
        name = branch.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
