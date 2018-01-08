package com.huaihua.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jsq on 2018/1/8.
 */
@Service
public class UserService implements UserDetailsService{

    public final static Map<String,User> map = new ConcurrentHashMap<>();
    static{
        map.put("jsq",new User("jsq","jsq",Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = map.get(username);
        if(null==user){
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }


}
