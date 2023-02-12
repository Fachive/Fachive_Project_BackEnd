package com.facaieve.backend.Constant;

public enum PostType {

    PORTFOLIO("PORTFOLIO"),
    FASHIONPICKUP("FASHIONPICKUP"),
    FUNDING("FUNDING"),
    PortfolioComment("PORTFOLIO COMMENT"),
    FashionPickUpComment("FASHIONPICKUP COMMENT"),
    FundingComment("FUNDING COMMENT");

    private String type;

    PostType(String postType){
        this.type = postType;
    }

    public String getPostType(){
        return this.type;
    }
}
