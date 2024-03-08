package keycloakinitializer.realm.corn;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

public class PlaceholderUsers {

    private static final String MAIL_SUFFIX = "@mail.pl";
    private static final String DEFAULT_ROLE = "default-roles-corn";

    public static List<UserRepresentation> generate() {
        return Stream.of(
                new SimpleUser("Jan", "Kowalski", "jan", "123"),
                new SimpleUser("Andrzej", "Switch", "andrzej", "123"),
                new SimpleUser("John", "Doe", "john", "123"),
                new SimpleUser("Jane", "Doe", "jane", "123"),
                new SimpleUser("Alice", "Smith", "alice", "123"),
                new SimpleUser("Bob", "Johnson", "bob", "123")
        ).map(SimpleUser::intoUserRepresentation).toList();
    }

    private record SimpleUser(String name, String surname, String username, String password) {

        UserRepresentation intoUserRepresentation() {
            return new UserRepresentation() {{
                setUsername(username());
                setFirstName(name);
                setLastName(surname);
                setEmail(username.toLowerCase()+MAIL_SUFFIX);
                setEmailVerified(true);
                setEnabled(true);
                setRealmRoles(List.of(DEFAULT_ROLE));
                setCredentials(singletonList(new CredentialRepresentation() {{
                    setType(CredentialRepresentation.PASSWORD);
                    setValue(password());
                }}));
            }};
        }

    }
}