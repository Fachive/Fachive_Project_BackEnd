package com.facaieve.backend.Constant;

public enum PostType {

    PORTFOLIO("PORTFOLIO"),
    FASHIONPICKUP("FASHIONPICKUP"),
    FUNDING("FUNDING");

    private String type;

    PostType(String postType){
        this.type = postType;
    }

    public String getPostType(){
        return this.type;
    }
}
