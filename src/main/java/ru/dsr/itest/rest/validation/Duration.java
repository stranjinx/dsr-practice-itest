package ru.dsr.itest.rest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Timestamp;
import java.time.Instant;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DurationValidator.class)
public @interface Duration {
    String message() default "";
    Target target() default Target.ANY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    enum Target{
        FUTURE {
            @Override
            public boolean isValid(Timestamp from, Timestamp to) {
                return super.isValid(from, to) && Timestamp.from(Instant.now()).before(from);
            }
        }, PAST {
            @Override
            public boolean isValid(Timestamp from, Timestamp to) {
                return super.isValid(from, to) && Timestamp.from(Instant.now()).after(from);
            }
        }, ANY;

        public  boolean isValid(Timestamp from, Timestamp to) {
            return from.before(to);
        }
    }
}
