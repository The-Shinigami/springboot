package com.first.firstproject.controller;

import com.first.firstproject.config.auth.JwtTokenUtil;
import com.first.firstproject.dto.UserDto;
import com.first.firstproject.dto.auth.JwtRequest;
import com.first.firstproject.dto.auth.JwtResponse;
import com.first.firstproject.entity.User;
import com.first.firstproject.exception.response.Response;
import com.first.firstproject.service.auth.JwtUserDetailsService;
import com.first.firstproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello world";
    }

//    ----------------------------------------------
@PostMapping("/authenticate")
public ResponseEntity<Response> createAuthenticationToken(@RequestBody  UserDto userDto) throws Exception {

    System.out.println(userDetailsService.loadUserByUsername(userDto.getUsername()).toString());

    authenticate(userDto.getUsername(),userDto.getPassword());

    final UserDetails userDetails = userDetailsService
            .loadUserByUsername(userDto.getUsername());



 final String token = "Bearer "+jwtTokenUtil.generateToken(userDetails);
 User user = userService.getByUsername(userDto.getUsername());

    Map<String,Object> userToken = new HashMap<>();

    userToken.put("user",user);
    userToken.put("token",token);

//   return ResponseEntity.ok(new JwtResponse(token));
    return new ResponseEntity<Response>(Response.ok().setPayload(userToken), HttpStatus.OK);
}
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}

