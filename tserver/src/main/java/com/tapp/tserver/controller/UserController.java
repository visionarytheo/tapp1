package com.tapp.tserver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tapp.tserver.dto.ResDto;
import com.tapp.tserver.model.User;
import com.tapp.tserver.service.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/sync")
    public ResponseEntity<ResDto> syncUser(@AuthenticationPrincipal Jwt jwt) {

        User userFromToken = new User(
                null,
                jwt.getClaimAsString("given_name"),
                jwt.getClaimAsString("family_name"),
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("picture"),
                null,
                null,
                null);

        service.syncUser(userFromToken);

        ResDto res = ResDto.success("Synced User");

        return new ResponseEntity<ResDto>(res, HttpStatus.OK);

    }

    @GetMapping("/role/{email}")
    public ResponseEntity<ResDto> getUserRoleByEmail(@PathVariable("email") String email) {

        String data = service.getUserRoleByEmail(email);
        ResDto res = ResDto.success("Fetched Role", data);

        return new ResponseEntity<ResDto>(res, HttpStatus.OK);

    }

    @GetMapping("/{email}")
    public ResponseEntity<ResDto> getUserByEmail(@PathVariable("email") String email) {
        User data = service.getUserByEmail(email);
        ResDto res = ResDto.success("Fetched User", data);

        return new ResponseEntity<ResDto>(res, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ResDto> getAllUsers(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");

        String role = service.getUserRoleByEmail(email);

        if (!"ADMIN".equals(role)) {
            ResDto res = ResDto.error("Not Authorized");
            return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }

        List<User> data = service.getAllUsers();
        ResDto res = ResDto.success("Fetched Users", data);

        return new ResponseEntity<ResDto>(res, HttpStatus.OK);
    }

}
