package com.daemawiki.daemawiki.common.annotation;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.*;

@Document
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Entity {

}
