package com.gulyaich.news.kafkanews.model.web.request;

import javax.validation.constraints.NotBlank;

public class SomePrivateThingCreationRequest {

    @NotBlank
    private String securedData;

    public String getSecuredData() {
        return securedData;
    }

    public void setSecuredData(String securedData) {
        this.securedData = securedData;
    }
}
