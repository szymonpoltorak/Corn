package dev.corn.cornbackend.config.jwtprocessing;

import dev.corn.cornbackend.api.user.interfaces.UserService;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class Jwt2AuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final Converter<Jwt, Collection<? extends GrantedAuthority>> authoritiesConverter;
    private final Converter<Jwt, User> userConverter;
    private final UserService userService;

    @Override
    public final JwtAuthenticationToken convert(Jwt jwt) {
        User user = userConverter.convert(jwt);

        if(user != null) {
            return new JwtAuthenticationToken(jwt, user.getAuthorities(), user.getUsername());
        }
        UserResponse userResponse = registerUserOnFirstLogin(jwt);

        return new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt), userResponse.username());
    }

    private UserResponse registerUserOnFirstLogin(Jwt jwt) {
        String name = JwtClaim.NAME.getClaim(jwt);
        String surname = JwtClaim.SURNAME.getClaim(jwt);
        String username = JwtClaim.USERNAME.getClaim(jwt);

        return userService.registerUser(name, surname, username);
    }

}
