package dev.corn.cornbackend.config.jwtprocessing;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import static dev.corn.cornbackend.config.jwtprocessing.JwtProcessingUtil.getName;
import static dev.corn.cornbackend.config.jwtprocessing.JwtProcessingUtil.getPrincipalClaimName;
import static dev.corn.cornbackend.config.jwtprocessing.JwtProcessingUtil.getSurname;

@Component
@RequiredArgsConstructor
public class Jwt2AuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final Jwt2AuthoritiesConverter authoritiesConverter;
    private final Jwt2UserConverter userConverter;
    private final UserService userService;

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        User user = userConverter.convert(jwt);
        if(user == null) {
            user = registerUserOnFirstLogin(jwt);
        }
        return new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt), user.getUsername());
    }

    private User registerUserOnFirstLogin(Jwt jwt) {
        String name = getName(jwt);
        String surname = getSurname(jwt);
        String username = getPrincipalClaimName(jwt);
        return userService.registerUser(name, surname, username);
    }

}
