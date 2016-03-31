package business.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.UserWrapper;
import data.daos.AuthorizationDao;
import data.daos.UserDao;
import data.entities.Authorization;
import data.entities.Role;
import data.entities.User;

@Controller
public class UserController {

    private UserDao userDao;

    private AuthorizationDao authorizationDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setAuthorizationDao(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    public boolean registration(UserWrapper userWrapper) {
        if (null == userDao.findByUsernameOrEmail(userWrapper.getUsername())
                && null == userDao.findByUsernameOrEmail(userWrapper.getEmail())) {
            User user = new User(userWrapper.getUsername(), userWrapper.getEmail(), userWrapper.getPassword(), userWrapper.getBirthDate());
            userDao.save(user);
            authorizationDao.save(new Authorization(user, Role.PLAYER));
            return true;
        } else {
            return false;
        }
    }
    
    public List<UserWrapper> getUsers() {
        List<UserWrapper> userWrappers = new ArrayList<>();
        for (User u:userDao.findAll()) {
            userWrappers.add(new UserWrapper(u.getUsername(), u.getEmail(), u.getPassword(), u.getBirthDate()));
        }
        return userWrappers;
    }
    
    public UserWrapper getUser(String username) {
        User user = userDao.findByUsernameOrEmail(username);
        UserWrapper userWrapper = new UserWrapper(user.getUsername(), user.getEmail(), user.getPassword(), user.getBirthDate());
        return userWrapper;
    }
    
    public User getUserInstance(String username) {
        return userDao.findByUsernameOrEmail(username);
    }
    
   
    
}
