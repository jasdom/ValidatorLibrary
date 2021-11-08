package com.jasdom.user_module.user_module.service;

public class ServiceResponse<T> {

    public boolean success;
    public T data;
    public String message;

    public ServiceResponse(T data){
        this(true, data, "");
    }

    public ServiceResponse(boolean success, T data, String message){
        this.success = success;
        this.data = data;
        this.message = message;
    }

}
