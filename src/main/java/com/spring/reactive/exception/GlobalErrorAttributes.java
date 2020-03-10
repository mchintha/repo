package com.spring.reactive.exception;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);

        if (getError(request) instanceof BadRequestExceptionFlux) {
            BadRequestExceptionFlux ex = (BadRequestExceptionFlux) getError(request);
            map.put("exception", ex.getClass().getSimpleName());
            map.put("message", ex.getMessage());
            map.put("status", ex.getStatus().value());
            map.put("error", ex.getStatus().getReasonPhrase());

            return map;
        }else if(getError(request) instanceof DuplicateKeyException) {
            DuplicateKeyException duplicateKeyException = (DuplicateKeyException) getError(request);
            map.put("message", duplicateKeyException.getMessage());
            return map;
        }

        map.put("exception", "SystemException");
        map.put("message", "System Error , Check logs!");
        map.put("status", "500");
        map.put("error", " System Error ");
        return map;
    }
}