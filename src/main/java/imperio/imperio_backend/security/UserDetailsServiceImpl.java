package imperio.imperio_backend.security;

import imperio.imperio_backend.login.module.LoginModule;
import imperio.imperio_backend.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginRepository loginRepository;

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look for the user in your 'users' table
        LoginModule user = loginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Return a Spring Security User object
        return new User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // You can add roles here later if needed
        );
    }
}