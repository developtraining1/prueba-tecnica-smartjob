package com.smart.data.msuser.service.impl;

import com.smart.data.msuser.entity.ApiResponse;
import com.smart.data.msuser.entity.AuthSecurityRequest;
import com.smart.data.msuser.entity.User;
import com.smart.data.msuser.entity.UserSecurityInfo;
import com.smart.data.msuser.exception.NotAuthorizedCustomException;
import com.smart.data.msuser.exception.NotUniqueEmailCustomException;
import com.smart.data.msuser.exception.ResourceNotFoundCustomException;
import com.smart.data.msuser.repository.UserRepository;
import com.smart.data.msuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSecurityInfoService userSecurityInfoService;

    @Autowired
    private JwtService jwtService;

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization").substring(7);
        String nameSecurity = jwtService.extractUsername(token);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                return List.of(userRepository.findByEmail(nameSecurity)
                        .map(user -> {
                            user.setLastLogin(LocalDateTime.now());
                            return ApiResponse.mutate(userRepository.save(user));
                        })
                        .orElseThrow(ResourceNotFoundCustomException::new));
        }

        userRepository.findAll()
                .forEach(user -> users.add(user));
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization").substring(7);
        String nameSecurity = jwtService.extractUsername(token);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            if(email.equals(nameSecurity)) {
                return userRepository.findByEmail(email)
                        .map(user -> {
                            user.setLastLogin(LocalDateTime.now());
                            return ApiResponse.mutate(userRepository.save(user));
                        });
            } else {
                throw new NotAuthorizedCustomException();
            }
        }

        return userRepository.findByEmail(email).map(ApiResponse::mutate);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User createUser(User user) {
        validateEmail(user);

        userSecurityInfoService.addUser(buildUserSecurity(user));

        return ApiResponse.mutate(userRepository.save(buildUser(user)));
    }

    public User updateUser(User user) {
        user.setModified(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    private User buildUser(User user) {
        String token = userSecurityInfoService.getAuthenticationToken(buildAuthSecurityRequest(user));
        user.setToken(token);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsActive(true);
        return user;
    }

    private UserSecurityInfo buildUserSecurity(User user) {

        return UserSecurityInfo.builder()
                .name(user.getEmail())
                .password(user.getPassword())
                .roles("ROLE_USER")
                .build();
    }

    private AuthSecurityRequest buildAuthSecurityRequest(User user) {

        return AuthSecurityRequest.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
    private void validateEmail(User user) {
        if(!userRepository.findByEmail(user.getEmail().trim()).isEmpty()) {
            throw new NotUniqueEmailCustomException();
        }
    }
}
