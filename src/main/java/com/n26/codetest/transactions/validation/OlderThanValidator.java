package com.n26.codetest.transactions.validation;

import org.springframework.lang.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.util.function.Supplier;

public class OlderThanValidator implements ConstraintValidator<OlderThan, Long> {

    public static Supplier<Long> NOW = System::currentTimeMillis;

    private OlderThan annotation;

    @Override
    public void initialize(OlderThan annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(@Nullable Long value, ConstraintValidatorContext context) {
        Duration age = Duration.of(annotation.duration(), annotation.unit());
        return value == null || NOW.get() - value <= age.toMillis();
    }

}
