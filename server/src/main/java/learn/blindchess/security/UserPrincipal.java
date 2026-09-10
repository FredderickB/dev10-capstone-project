package learn.blindchess.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Integer userId;
    private final String guestIdentifier;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer userId, String role) {
        this.userId = userId;
        this.guestIdentifier = null;
        this.authorities = List.of(new SimpleGrantedAuthority(role));
    }

    public UserPrincipal(String guestIdentifier, String role) {
        this.userId = null;
        this.guestIdentifier = guestIdentifier;
        this.authorities = List.of(new SimpleGrantedAuthority(role));
    }

    public Integer getUserId() {
        return userId;
    }

    public String getGuestIdentifier() {
        return guestIdentifier;
    }

    public boolean isGuest() {
        return guestIdentifier != null;
    }

    @Override
    public String getUsername() {
        return isGuest() ? guestIdentifier : String.valueOf(userId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

}