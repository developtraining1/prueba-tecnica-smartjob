package com.smart.data.msuser.controller;

import com.smart.data.msuser.entity.AuthSecurityRequest;
import com.smart.data.msuser.entity.UserSecurityInfo;
import com.smart.data.msuser.service.impl.UserSecurityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserSecurityController {
    @Autowired
    private UserSecurityInfoService userSecurityInfoService;

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserSecurityInfo userSecurityInfo) {
        return userSecurityInfoService.addUser(userSecurityInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthSecurityRequest authSecurityRequest) {
        return userSecurityInfoService.getAuthenticationToken(authSecurityRequest);
    }
}
