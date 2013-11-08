package com.thoughtworks.bbs.service;

import java.util.Map;

public class ServiceResult<T extends Object> {
    private Map<String, String> errors;
    private T model;

    public ServiceResult(Map<String, String> errors, T model) {
        this.errors = errors;
        this.model = model;
    }

    public Map<String,String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public T getModel() {
        return model;
    }
}
