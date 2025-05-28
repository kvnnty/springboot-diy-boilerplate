package com.kvn.starter.v1.validation.annotations;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kvn.starter.v1.validation.validators.PhoneNumberConstraintValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Constraint(validatedBy = PhoneNumberConstraintValidator.class)
public @interface ValidPhoneNumber {
  String message() default "Please enter a valid phone number";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
