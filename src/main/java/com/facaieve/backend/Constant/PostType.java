package com.facaieve.backend.Constant;

import lombok.Getter;

public enum PostType {

    PORTFOLIO("PORTFOLIO"),
    FASHIONPICKUP("FASHIONPICKUP"),
    FUNDING("FUNDING");
    @Getter
    private String type;

    PostType(String postType){
        this.type = postType;
    }

    public String getPostType(){
        return this.type;
    }
}
