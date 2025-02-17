package com.inn.caffe.serviceImpl;

import com.inn.caffe.POJO.User;
import com.inn.caffe.constants.CafeConstants;
import com.inn.caffe.dao.UserDao;
import com.inn.caffe.service.UserService;
import com.inn.caffe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

//    cntrl+alt+shift+L
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
      try{
          if(validateSignUpMap(requestMap)){
              User user = userDao.findByEmailId(requestMap.get("email"));
              if(Objects.isNull(user)){
                  userDao.save(getUserFromMap(requestMap));
                  return CafeUtils.getResponseEntity("Successfully Registered.", HttpStatus.OK);
              }else{
                  return CafeUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
              }
          }else{
              return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
          }
      }catch (Exception ex){
          ex.printStackTrace();
      }
      return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;

    }
}

