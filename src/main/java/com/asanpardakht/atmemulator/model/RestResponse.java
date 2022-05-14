package com.asanpardakht.atmemulator.model;

import lombok.ToString;

@ToString
public class RestResponse {
    private RestResponseType code;
    private Object message;
    public RestResponse(RestResponseType code, Object message) {
        this.code = code;
        this.message = message;
    }

    public RestResponse() {
    }

    public RestResponseType getCode() {
        return code;
    }

    public void setCode(RestResponseType code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
