package com.iyzico.challenge.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LocaleService {

    @Autowired
    private MessageSource messageSource;

    private static MessageSource staticMessageSource;

    @PostConstruct
    private void init() {
        staticMessageSource = this.messageSource;
    }

    public static String getMessage(String code) {
        return staticMessageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, String... params) {
        return staticMessageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }
}
