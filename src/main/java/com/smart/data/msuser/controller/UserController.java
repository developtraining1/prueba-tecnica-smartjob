package com.smart.data.msuser.controller;

import com.smart.data.msuser.entity.ApiResponse;
import com.smart.data.msuser.entity.User;
import com.smart.data.msuser.exception.ResourceNotFoundCustomException;
import com.smart.data.msuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getUsers() {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                .message("Listado de usuarios satisfactorio")
                .response(userService.getUsers())
                .build());
    }

    @GetMapping("/find/{email}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String email) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                .message("Usuario encontrado")
                .response(userService.findByEmail(email).orElseThrow(ResourceNotFoundCustomException::new))
                .build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createUsuario(@RequestBody User user) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                .message("Usuario creado satisfactoriamente")
                .response(userService.createUser(user))
                .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateUsuario(@RequestBody User user) {
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Usuario modificado")
                        .response(userService.updateUser(user))
                        .build());
    }
}
