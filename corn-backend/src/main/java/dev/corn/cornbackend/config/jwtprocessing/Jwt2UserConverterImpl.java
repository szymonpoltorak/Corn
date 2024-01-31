package dev.corn.cornbackend.config.jwtprocessing;

import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Jwt2UserConverterImpl implements Converter<Jwt, User> {

    private final UserRepository userRepository;
    private final Converter<Jwt, Collection<? extends GrantedAuthority>> authoritiesConverter;

    @Override
    public final User convert(Jwt jwt) {
        String username = JwtClaim.USERNAME.getClaim(jwt);
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();

        user.setAuthorities(authoritiesConverter.convert(jwt));

        return user;
    }

}
