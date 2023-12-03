package dev.corn.cornbackend.config.jwtprocessing;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static dev.corn.cornbackend.config.jwtprocessing.JwtProcessingUtil.getPrincipalClaimName;

@Component
@RequiredArgsConstructor
public class Jwt2UserConverter implements Converter<Jwt, User> {

    private final UserRepository userRepository;
    private final Jwt2AuthoritiesConverter authoritiesConverter;

    @Override
    public User convert(Jwt jwt) {
        String principalClaimName = getPrincipalClaimName(jwt);
        Optional<User> optionalUser = userRepository.findByUsername(principalClaimName);
        if(optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        user.setAuthorities(authoritiesConverter.convert(jwt));
        return user;
    }

}
