package com.api.quiz.controller;

import com.api.quiz.config.JwtTokenUtil;
import com.api.quiz.exception.RegisterException;
import com.api.quiz.exception.RegisterSuccessful;
import com.api.quiz.model.JwtRequest;
import com.api.quiz.model.JwtResponse;
import com.api.quiz.model.User;
import com.api.quiz.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/*
Expose a POST API /authenticate using the JwtAuthenticationController. The POST API gets username and password in the
body- Using Spring Authentication Manager we authenticate the username and password.If the credentials are valid,
a JWT token is created using the JWTTokenUtil and provided to the client.
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {




    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        String tempEmail = user.getEmail();
        String tempFirstname = user.getFirstname();
        String tempLastname = user.getLastname();
        String tempUsername = user.getUsername();
        String tempPassword = user.getPassword();
        if (tempEmail != null && !"".equals(tempEmail) && tempEmail.contains("@") && !"".equals(tempFirstname) && !"".equals(tempLastname) && !"".equals(tempUsername) && !"".equals(tempPassword)) {
            User userobj = userDetailsService.fetchUserByUsernameAndEmail(tempUsername, tempEmail);
            if (userobj != null) {
                throw new RegisterException("Register Failed, User With " + tempEmail + " or "+ tempUsername + " already taken!");
            }
            ResponseEntity.ok(userDetailsService.save(user));
            throw new RegisterSuccessful("Register Successful");
        }
        else {
            throw new RegisterException("Register Failed, Are You Missing Something?");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    private void login(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}