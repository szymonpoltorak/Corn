package dev.corn.cornbackend.config.jwtprocessing;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "@jwt2UserConverter.convert(#this)")
public @interface JwtAuthed {
}
