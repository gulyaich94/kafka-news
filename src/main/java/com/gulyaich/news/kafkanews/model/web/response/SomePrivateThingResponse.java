package com.gulyaich.news.kafkanews.model.web.response;

import javax.validation.constraints.NotBlank;

public class SomePrivateThingResponse {

    public SomePrivateThingResponse(@NotBlank Long id, @NotBlank String privateData) {
        this.id = id;
        this.privateData = privateData;
    }

    @NotBlank
    private Long id;

    @NotBlank
    private String privateData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivateData() {
        return privateData;
    }

    public void setPrivateData(String privateData) {
        this.privateData = privateData;
    }
}
