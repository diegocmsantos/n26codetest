package com.n26.codetest.transactions.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OlderThanValidatorTest {

    @Mock
    private OlderThan annotation;

    @Mock
    private ConstraintValidatorContext context;

    private OlderThanValidator validator;

    @Before
    public void setUp() {
        OlderThanValidator.NOW = System::currentTimeMillis;
        validator = new OlderThanValidator();
        validator.initialize(annotation);
        when(annotation.duration()).thenReturn(60);
        when(annotation.unit()).thenReturn(SECONDS);
    }

    @Test
    public void youngerThan60SecTest() {
        boolean valid = validator.isValid(System.currentTimeMillis() - 59000, context);
        assertThat(valid, is(true));
    }

    @Test
    public void olderThan60SecTest() {
        boolean valid = validator.isValid(System.currentTimeMillis() - 61000, context);
        assertThat(valid, is(false));
    }

}
