package com.service;

import com.modelUser.User;
import com.repositoryUser.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author ：Yimyl
 * @date ：Created in 2019/4/27 9:48
 * @description：
 * @modified By：
 * @version: $
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：验证用户账号和密码
     * @param      ：
     * @return     ：Object[0]是查询状态
     *              Object[1]是查询到的User
     */
    public Object[] login(String username, String password) {
        String info = null;
        User user1 = null;
        Object[] ret = new Object[2];
        try {
            user1 = userRepository.findByUsernameAndPassword(username, password);
            if (user1 != null) {
                info =  "login success";
                ret[1] = user1;
            }else {
                info = "username or password error";
            }
            ret[0] = info;
        } catch (Exception e) {
            log.error("bjtu.service.UserService.login:", e);
            info = "register error";
            ret[0] = info;
        }finally {
            return ret;
        }

    }
    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：注册用户
     * @param      ：
     * @return     ：注册的状态
     */
    public String register(String username, String password) {
        try {
            if(isUsernameCanRegister(username) == false)return "Username has been registered!";
            User user = User.builder().username(username).password(password).build();
            userRepository.save(user);
            return "register success";
        } catch (Exception e) {
            log.error("bjtu.service.UserService.register :", e);
            return "register fail";
        }
    }
    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：修改密码
     * @param      ：modifyBy：若为Integer，则按照id查询用户，并修改；若为String，则按照username查询用户并修改
     * @return     ：修改状态
     */
    public String modifyPassword(Object modifyBy, String password) {
        try {
            if(modifyBy instanceof String){
                User user = userRepository.findByUsername((String) modifyBy);
                user.setPassword(password);
                userRepository.save(user);
                return "modify success";
            } else if(modifyBy instanceof Integer){
                Optional<User> optional = userRepository.findById((Integer) modifyBy);
                User user = optional.get();
                user.setPassword(password);
                userRepository.save(user);
                return "modify success";
            }
        } catch (Exception e) {
            log.error("bjtu.service.UserService.modifyPassword :", e);
        }
        return "modify error";
    }
    /**
     * @author     ：Yimyl
     * @date       ：Created in 2019.4.27
     * @desc       ：用户名是否可以注册
     * @param      ：
     * @return     ：false：已使用，不可以注册
     *              true：未使用，可以注册
     */

    public boolean isUsernameCanRegister(String username){
        try {
            User user1 = userRepository.findByUsername(username);
//            log.info("user1 : {}",user1);
            if (user1 == null) {
                return true;
            }
        } catch (Exception e) {
            log.error("bjtu.service.UserService.isUsernameCanRegister :", e);
        }
        return false;
    }

}
