package task.smartsoft.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import task.smartsoft.domain.User;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
