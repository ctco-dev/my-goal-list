package lv.ctco.javaschool.auth.entity.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ErrorDto {
    @JsonbProperty
    private String errorCode;
    @JsonbProperty(nillable = true)
    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
