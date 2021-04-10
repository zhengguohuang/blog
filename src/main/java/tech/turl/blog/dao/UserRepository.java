package tech.turl.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.turl.blog.po.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}
