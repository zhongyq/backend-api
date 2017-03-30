package cn.edu.nju.backend.server.common.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhongyq on 17/3/1.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    AuthLevel value() default AuthLevel.ALL;

    AuthLevel level() default AuthLevel.ALL;

    int role() default 0;

    int[] roles() default {};
}
