package dev.corn.cornbackend.config.jwtprocessing;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static dev.corn.cornbackend.config.jwtprocessing.JwtProcessingUtil.getPrincipalClaimName;

@Component
@RequiredArgsConstructor
public class Jwt2UserConverter implements Converter<Jwt, User> {

    private final UserRepository userRepository;

    @Override
    public User convert(Jwt jwt) {
        String principalClaimName = getPrincipalClaimName(jwt);
        return userRepository.findUserByUsername(principalClaimName).orElse(null);
    }

}
