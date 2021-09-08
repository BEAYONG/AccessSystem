package com.nubomed.AccessSystem.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhanglei
 * @date 2021/8/23 15:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WebSocketMapping {

    String value() default "";
}
