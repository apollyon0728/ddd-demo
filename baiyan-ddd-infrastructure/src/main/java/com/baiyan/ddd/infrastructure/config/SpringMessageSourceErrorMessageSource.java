package com.baiyan.ddd.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author baiyan
 */
@Component
public class SpringMessageSourceErrorMessageSource {

    /**
     * 用于解析消息的策略接口，支持此类消息的参数化和国际化。
     * Spring 为生产环境提供了两种开箱即用的实现：
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * 根据指定的代码和参数获取消息。
     *
     * @param code   指定的代码，用于查找相应的消息。
     * @param params 消息中要替换的参数。
     * @return 返回根据指定代码和参数获取的消息。
     */
    public String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }

    /**
     * 获取消息的方法，根据给定的code和参数获取对应的消息。
     *
     * @param code             消息的code
     * @param defaultMessage  默认消息
     * @param params           消息的参数
     * @return                 根据code和参数获取的消息
     */
    public String getMessage(String code, String defaultMessage, Object... params) {
        return messageSource.getMessage(code, params, defaultMessage, LocaleContextHolder.getLocale());
    }
}
