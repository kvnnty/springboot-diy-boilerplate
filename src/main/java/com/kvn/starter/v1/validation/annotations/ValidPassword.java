package com.kvn.starter.v1.validation.annotations;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kvn.starter.v1.validation.validators.PasswordConstraintValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface ValidPassword {
  String message() default "Invalid Password";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
