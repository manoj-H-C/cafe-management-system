package com.inn.caffe.rest;

import com.inn.caffe.constants.CafeConstants;
import com.inn.caffe.service.UserService;
import com.inn.caffe.utils.CafeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(path = "/user")
@RestController
public class UserRest {

    final UserService userService;

    public UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
              return userService.signUp(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        return new ResponseEntity<String>("{\"message\":\"something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
