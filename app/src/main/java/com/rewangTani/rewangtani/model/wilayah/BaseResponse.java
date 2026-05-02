package com.rewangTani.rewangtani.model.wilayah;

import java.util.List;

public class BaseResponse<T> {
    private String status;
    private String message;
    private List<T> value;

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public List<T> getValue() { return value; }
}