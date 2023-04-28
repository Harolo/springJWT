package com.springJWT.Service.implement;

import com.springJWT.Model.User;
import com.springJWT.Repository.UserRepository;
import com.springJWT.Service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImp implements UsuarioService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
