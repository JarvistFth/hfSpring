package com.example.hfspring.Model;

public class ResponseCode {
    private String Code;

    public ResponseCode(String code, String msg) {
        Code = code;
        Msg = msg;
    }
    public ResponseCode() {

    }

    private String Msg;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
