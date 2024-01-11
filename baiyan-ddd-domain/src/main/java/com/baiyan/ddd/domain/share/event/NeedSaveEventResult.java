package com.baiyan.ddd.domain.share.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保存事件结果解析注解
 *
 * @author baiyan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface NeedSaveEventResult {
}


/**
 *  NeedSaveEventHandlerAspect 中对该注解做了AOP
 *
 @Pointcut("@annotation(com.baiyan.ddd.domain.share.event.NeedSaveEventResult)")
 public void pointcut() {
 }
 */