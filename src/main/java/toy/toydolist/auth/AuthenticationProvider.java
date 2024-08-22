package toy.toydolist.auth;

public interface AuthenticationProvider {
    AuthenticationResult authenticate(String username, String password);
}
