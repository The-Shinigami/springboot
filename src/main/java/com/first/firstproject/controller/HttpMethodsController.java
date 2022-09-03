package com.first.firstproject.controller;

import com.first.firstproject.dto.UserDto;
import com.first.firstproject.exception.response.Response;
import com.first.firstproject.exception.validation.MapValidationErrorService;
import com.first.firstproject.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/")
@CrossOrigin("*")
public class HttpMethodsController {

    @Autowired
    private  MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    public HttpMethodsController() {
    }

    //    guery string
    @GetMapping("/param")
    public String getUrlParam(@RequestParam(required = true)  String name){
       return "my name is " + name;
    }
//    query parameters
    @GetMapping("/variable/{name}")
    public String getUrlAttribute(@PathVariable("name") String name){
        return "my name is " + name;
    }

    //  user model handle error
    @PostMapping("/user")
    public ResponseEntity setUser(@RequestBody @Valid UserDto user, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) return mapValidationErrorService.mapValidationService(bindingResult);
        user.setId(userServiceImpl.getNewId());
        userServiceImpl.save(user);

        return new ResponseEntity<Response>(Response.ok().setPayload(user), HttpStatus.OK);
    }
//   get all users
    @GetMapping("/user")
    public ResponseEntity getUsers()  {

       return new ResponseEntity<Response>(Response.ok().setPayload(userServiceImpl.getAll()), HttpStatus.OK);
    }

}
