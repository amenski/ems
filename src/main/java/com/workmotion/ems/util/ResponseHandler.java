package com.workmotion.ems.util;

import java.util.Objects;

import it.aman.ethjournal.swagger.model.ResponseBase;

public class ResponseHandler {
    public <T extends ResponseBase> T fillSuccessResponse(T response) {
        Objects.requireNonNull(response);
        response.success(true);
        response.resultCode(Constants.SUCCESS);
        response.message("");
        response.errors(null);

        return response;
    }

    public <T extends ResponseBase> T fillSuccessResponseWithMessage(Class<T> response, String message) {
        T resp = getNewInstance(response);
        resp.success(true);
        resp.resultCode(Constants.SUCCESS);
        resp.message(message);
        resp.errors(null);

        return resp;
    }

    public <T extends ResponseBase> T fillFailResponseEMSException(Class<T> response, EMSException e) {
        T res = getNewInstance(response);
        res.success(false);
        res.resultCode(e.getInternalCode());
        res.message(e.getErrorMessage() != null ? e.getErrorMessage() : "");
        res.errors(e.getErrors());

        return res;
    }

    public <T extends ResponseBase> T fillFailResponseGeneric(Class<T> response1) {
        T response = getNewInstance(response1);
        response.success(false);
        response.resultCode(ExceptionEnums.UNHANDLED_EXCEPTION.get().getInternalCode());
        response.message(ExceptionEnums.UNHANDLED_EXCEPTION.get().getMessage());
        response.errors(null);

        return response;
    }

    @SuppressWarnings("unchecked")
    private <T> T getNewInstance(Class<T> clazz) {
        T newInstance;
        try {
            newInstance = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            newInstance = (T) new ResponseBase();
        }
        return newInstance;
    }
}