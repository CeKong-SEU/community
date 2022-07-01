package com.nowcoder.community.util;


import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();//以线程为key 存取值

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
