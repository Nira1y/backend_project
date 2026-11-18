package backend_project.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import backend_project.demo.dto.UserRequest;
import backend_project.demo.mapper.UserMapper;
import backend_project.demo.model.Role;
import backend_project.demo.model.User;
import backend_project.demo.model.UserEntity;
import backend_project.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User register(UserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username taken");
        }
        
        UserEntity entity = new UserEntity();
        entity.setUsername(request.username());
        entity.setPassword(passwordEncoder.encode(request.password())); 
        entity.setRole(Role.USER);
        UserEntity saved = userRepository.save(entity);

        return userMapper.toDTO(saved);
    }

    public User findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        return userMapper.toDTO(user);
    }
}
