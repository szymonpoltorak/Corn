package keycloakinitializer.realm.corn;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

public class PlaceholderUsers {

    private static final String DEFAULT_ROLE = "default-roles-corn";

    public static List<UserRepresentation> generate() {
        return Stream.of(
                new SimpleUser("Jan", "Kowalski", "jan@gmail.com", "123"),
                new SimpleUser("Andrzej", "Switch", "andrzej@gmail.com", "123"),
                new SimpleUser("John", "Doe", "john@gmail.com", "123"),
                new SimpleUser("Jane", "Doe", "jane@gmail.com", "123"),
                new SimpleUser("Alice", "Smith", "alice@gmail.com", "123"),
                new SimpleUser("Bob", "Johnson", "bob@gmail.com", "123")
        ).map(SimpleUser::intoUserRepresentation).toList();
    }

    private record SimpleUser(String name, String surname, String username, String password) {

        UserRepresentation intoUserRepresentation() {
            return new UserRepresentation() {{
                setUsername(username());
                setFirstName(name);
                setLastName(surname);
                setEmail(username());
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