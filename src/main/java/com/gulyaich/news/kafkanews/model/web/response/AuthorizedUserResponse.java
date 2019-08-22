package com.gulyaich.news.kafkanews.model.web.response;

public class AuthorizedUserResponse {

    private JWTTokenResponse jwtTokenResponse;
    private String fullname;
    private String email;
    private String login;

    public JWTTokenResponse getJwtTokenResponse() {
        return jwtTokenResponse;
    }

    public void setJwtTokenResponse(JWTTokenResponse jwtTokenResponse) {
        this.jwtTokenResponse = jwtTokenResponse;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
