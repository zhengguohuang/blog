package tech.turl.blog.service;

import tech.turl.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
