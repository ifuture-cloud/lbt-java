package com.lordz.lbt.auth.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author ：zzz
 * @date ：Created in 7/24/19 3:04 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Role {

    String value();
}
