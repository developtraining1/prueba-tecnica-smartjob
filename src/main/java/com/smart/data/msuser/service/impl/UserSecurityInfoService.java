package com.smart.data.msuser.service.impl;

import com.smart.data.msuser.entity.AuthSecurityRequest;
import com.smart.data.msuser.entity.UserSecurityInfo;
import com.smart.data.msuser.entity.UserSecurityInfoDetails;
import com.smart.data.msuser.exception.NotUniqueEmailCustomException;
import com.smart.data.msuser.exception.PasswordPatternCustomException;
import com.smart.data.msuser.repository.UserSecurityInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserSecurityInfoService implements UserDetailsService {

    @Autowired
    private UserSecurityInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    private Pattern pattern;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserSecurityInfo> userDetail = repository.findByName(username);

        return userDetail.map(UserSecurityInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserSecurityInfo userSecurityInfo) {
        pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");

        if(!pattern.matcher(userSecurityInfo.getPassword()).matches()) {
            throw new PasswordPatternCustomException();
        }

        validateUserSecurityName(userSecurityInfo.getName());
        userSecurityInfo.setPassword(encoder.encode(userSecurityInfo.getPassword()));
        repository.save(userSecurityInfo);
        return "User Added Successfully";
    }

    public String getAuthenticationToken(AuthSecurityRequest authSecurityRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authSecurityRequest.getUsername(), authSecurityRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authSecurityRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    public void validateUserSecurityName(String name) {
        if(!repository.findByName(name).isEmpty()) {
            throw new NotUniqueEmailCustomException();
        }
    }
}
