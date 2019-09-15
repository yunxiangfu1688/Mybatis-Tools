package com.yxf.common.base.annotation;

import java.lang.annotation.*;

//@Target(ElementType.METHOD)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String value();
}
