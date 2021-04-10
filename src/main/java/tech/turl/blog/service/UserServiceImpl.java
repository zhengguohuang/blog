package tech.turl.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.turl.blog.dao.UserRepository;
import tech.turl.blog.po.User;
import tech.turl.blog.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired//注入
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
