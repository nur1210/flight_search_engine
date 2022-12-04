package fontys.s3.backend.configuration.websocket;

import java.security.Principal;

public class UserEmail implements Principal {
    private final String email;

    public UserEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return email;
    }
}
