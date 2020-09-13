package main.Response;

import main.dto.users.AuthUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthResponse implements Serializable {
    private Boolean result;
    private AuthUser user;
    private List<String> errors;
    private String secret;
    private String image;

    public AuthResponse() {

    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        this.errors = errors;
    }

    public void addError(String err) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(err);
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
