package com.evowork.viewmodel.user;

import com.evowork.entity.UserResource;

public class UserResourceModel {

    private Long id;
    private Integer type;
    private String pathToResource;
    private Integer imageSide;

    public UserResourceModel(UserResource userResource) {
        this.id = userResource.getId();
        if (userResource.getType() != null) {
            this.type = userResource.getType().getValue();
        }
        this.pathToResource = userResource.getPathToResource();
        if (userResource.getImageSide() != null) {
            this.imageSide = userResource.getImageSide().getValue();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public void setPathToResource(String pathToResource) {
        this.pathToResource = pathToResource;
    }

    public Integer getImageSide() {
        return imageSide;
    }

    public void setImageSide(Integer imageSide) {
        this.imageSide = imageSide;
    }
}
