package com.janaldous.offspringy.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.janaldous.offspringy.web.dto.UserRegistrationDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserRegistrationDto user = (UserRegistrationDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
