package com.kvn.starter.v1.validation.validators;

import com.kvn.starter.v1.validation.annotations.ValidNationalId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NationalIdConstraintValidator implements ConstraintValidator<ValidNationalId, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return false;
    return value.matches("^\\d{16}$");
  }
}