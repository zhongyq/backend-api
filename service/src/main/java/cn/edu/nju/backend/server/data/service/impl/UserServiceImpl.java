package cn.edu.nju.backend.server.data.service.impl;

import cn.edu.nju.backend.server.common.util.Encoder;
import cn.edu.nju.backend.server.data.dto.LoginDto;
import cn.edu.nju.backend.server.data.dto.UserDto;
import cn.edu.nju.backend.server.data.mapper.UserMapper;
import cn.edu.nju.backend.server.data.model.User;
import cn.edu.nju.backend.server.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhongyq on 17/3/8.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User login(LoginDto loginDto) {
        if(loginDto.getPassword() != null && loginDto.getPhone() != null) {
            User user = userMapper.login(loginDto.getPhone(), Encoder.encoderByMd5(loginDto.getPassword()));
            return user;
        }
        return null;
    }

    @Override
    public void register(UserDto userDto) {
        User user = new User();
        user.setCreditCard(userDto.getCreditCard());
        user.setIdentity(userDto.getIdentity());
        user.setPassword(Encoder.encoderByMd5(userDto.getPassword()));
        user.setPendingStatus(0);
        user.setSex(userDto.getSex());
        user.setRole(userDto.getRole());
        user.setPhone(userDto.getPhone());
        user.setUsername(userDto.getUsername());
        userMapper.insertUser(user);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void verifyDriver(Long id, int status) {
        userMapper.verifyDriver(id, status);
    }

    @Override
    public User getUserNameAndPhone(Long id) {
        return userMapper.getUserNameAndPhoneById(id);
    }
}
