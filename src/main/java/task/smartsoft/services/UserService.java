package task.smartsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.smartsoft.domain.Role;
import task.smartsoft.domain.User;
import task.smartsoft.repos.UserRepo;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        user.setRoles(Collections.singleton(Role.USER));

        return user;
    }

    public boolean addUser(User user){

        User userFromDB = userRepo.findByUsername(user.getUsername());

        if(userFromDB!=null){
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return true;
    }
}

