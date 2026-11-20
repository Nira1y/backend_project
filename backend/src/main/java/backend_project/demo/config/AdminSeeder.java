package backend_project.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import backend_project.demo.model.Role;
import backend_project.demo.model.UserEntity;
import backend_project.demo.repository.UserRepository;

@Component
public class AdminSeeder implements CommandLineRunner{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, @Value("${ADMIN_USERNAME}") String adminUsername, @Value("${ADMIN_PASSWORD}") String adminPassword){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args){
         if (userRepository.findByUsername(adminUsername).isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword)); 
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }


}
