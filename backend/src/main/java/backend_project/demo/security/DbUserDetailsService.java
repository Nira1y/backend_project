package backend_project.demo.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend_project.demo.model.UserEntity;
import backend_project.demo.repository.UserRepository;

@Service
public class DbUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public DbUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        var authority = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(), 
                List.of(authority)
        );
    }
}
