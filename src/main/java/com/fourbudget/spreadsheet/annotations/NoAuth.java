package com.fourbudget.spreadsheet.annotations;

import com.fourbudget.spreadsheet.annotations.enums.ValidationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
public @interface NoAuth {

    ValidationType validationType() default ValidationType.ANNONYMOUS;

}