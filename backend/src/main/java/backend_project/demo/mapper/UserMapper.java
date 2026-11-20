package backend_project.demo.mapper;

import org.springframework.stereotype.Component;

import backend_project.demo.model.User;
import backend_project.demo.model.UserEntity;

@Component
public class UserMapper {
    public User toDTO(UserEntity userEntity){
        if (userEntity == null) return null;
        return new User(userEntity.getId(), userEntity.getUsername(),userEntity.getRole());
    }
}
