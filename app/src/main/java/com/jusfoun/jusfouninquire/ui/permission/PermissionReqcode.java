package com.jusfoun.jusfouninquire.ui.permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionReqcode {
}
