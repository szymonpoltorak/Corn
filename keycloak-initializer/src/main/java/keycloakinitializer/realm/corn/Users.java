package keycloakinitializer.realm.corn;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

public class Users extends ArrayList<UserRepresentation> {

    public Users() {
        addAll(Stream.of(
                new SimpleUser("Pan", "Admin", "admin", "admin", CornRealm.Role.ADMIN),
                new SimpleUser("Użyt", "Kownik", "user", "user", CornRealm.Role.USER),
                new SimpleUser("Jan", "Kowalski", "jan", "jan", CornRealm.Role.USER)
        ).map(SimpleUser::intoUserRepresentation).toList());
    }

    private record SimpleUser(String name, String surname, String username, String password, CornRealm.Role... roles) {

        UserRepresentation intoUserRepresentation() {
            return new UserRepresentation() {{
                setUsername(username());
                setFirstName(name);
                setLastName(surname);
                setEnabled(true);
                setCredentials(singletonList(new CredentialRepresentation() {{
                    setType(CredentialRepresentation.PASSWORD);
                    setValue(password());
                }}));
                setRealmRoles(Arrays.stream(roles()).map(Enum::toString).toList());
            }};
        }

    }
}
